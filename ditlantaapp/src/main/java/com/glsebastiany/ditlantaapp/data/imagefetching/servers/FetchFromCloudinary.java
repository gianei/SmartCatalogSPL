/*
 *     SmartCatalogSPL, an Android catalog Software Product Line
 *     Copyright (c) 2016 Gianei Leandro Sebastiany
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.glsebastiany.ditlantaapp.data.imagefetching.servers;


import android.content.Context;
import android.util.Log;

import com.glsebastiany.ditlantaapp.BuildConfig;
import com.glsebastiany.ditlantaapp.data.FileServices;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.firebase.FirebaseItemImage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FetchFromCloudinary extends FetchFrom {

    private static final String LOG_TAG = FetchFromCloudinary.class.getName();

    private Context context;

    private Query query;
    private ValueEventListener valueEventListener;

    public FetchFromCloudinary(Context context) {
        super(context);
        this.context = context;
    }

    public void fetchFromServer(){
        try {
            List<String> fileNames = getImagesNeededFromFirebase();

            if (fileNames.size() == 0) {
                return;
            }

            query.removeEventListener(valueEventListener);

            imageFetcherNotification.setNeededImages(fileNames.size());
            imageFetcherNotification.informStatus();

            startAndWaitDownloaders(fileNames);

            imageFetcherNotification.finalSucess();

        } catch (InterruptedException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            imageFetcherNotification.finalError();

        }

    }

    private void startAndWaitDownloaders(List<String> fileNames) throws InterruptedException {
        int numberOfCores = Runtime.getRuntime().availableProcessors() * 16;
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(fileNames.size());
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(numberOfCores, numberOfCores * 2, 30, TimeUnit.MINUTES, queue);

        for (String fileName : fileNames) {
            Log.d(LOG_TAG, "Need file " + fileName);
            poolExecutor.execute(new ImageDownloader(fileName,
                    FileServices.getFullImageUrlFromFileName(context, fileName),
                    this));
        }

        poolExecutor.shutdown();
        poolExecutor.awaitTermination(6, TimeUnit.HOURS);
    }

    private List<String> getImagesNeededFromFirebase() throws InterruptedException {
        final List<String> fileNames = new ArrayList<>();

        DatabaseReference firebase = FirebaseDatabase.getInstance().getReferenceFromUrl(BuildConfig.FIREBASE_URL);

        if (!isConected()) {
            imageFetcherNotification.finalError();
            return fileNames;
        }


        final CountDownLatch latch = new CountDownLatch(1);
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshots) {

                for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {
                    FirebaseItemImage firebaseItemImage = dataSnapshot.getValue(FirebaseItemImage.class);

                    String imageFullUrl = FileServices.getFullImageUrlFromFileName(context, dataSnapshot.getKey() + ".jpg");
                    File file = new File(imageFullUrl);

                    if (!file.exists()) {
                        fileNames.add(dataSnapshot.getKey() + ".jpg");
                    } else if (file.lastModified() < firebaseItemImage.getUpdatedDate()) {
                        fileNames.add(dataSnapshot.getKey() + ".jpg");
                    } else {
                        Log.d(LOG_TAG, "File not needed " + imageFullUrl);
                    }
                }
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError DatabaseError) {
                latch.countDown();
                imageFetcherNotification.finalError();

            }

        };

        query = firebase.child(FirebaseItemImage.LOCATION);
        query.addValueEventListener(valueEventListener);

        latch.await();

        if (fileNames.size() == 0) {
            imageFetcherNotification.finalSucess();
        }


        return fileNames;

    }

    private boolean isConected() throws InterruptedException {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            return true;
        else
            return false;
    }

    private synchronized void increaseNotificatinProcessedImages(){
        imageFetcherNotification.increaseProcessedImages();
    }

    private synchronized void informStatus(){
        imageFetcherNotification.informStatus();
    }


    private class ImageDownloader implements Runnable {
        private final String fileName;
        private final String saveFileName;
        private final FetchFromCloudinary cloudinary;

        public ImageDownloader(final String fileName, final String saveFileName, FetchFromCloudinary cloudinary){
            this.fileName = fileName;
            this.saveFileName = saveFileName;
            this.cloudinary = cloudinary;
        }

        @Override
        public void run() {
            // Moves the current Thread into the background
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

            Log.e(LOG_TAG, "Downloading " + FileServices.getImageCompleteUrl() + fileName);
            byte[] bitmap  = FetchFromCloudinary.tryGetBitmapFromURL(FileServices.getImageCompleteUrl() + fileName);

            if (bitmap != null){
                Log.e(LOG_TAG, "Image downloaded, saving in " + saveFileName);
                FileServices.tryWriteToLocalFile(bitmap, saveFileName);

            }
            cloudinary.increaseNotificatinProcessedImages();
            cloudinary.informStatus();

        }
    }

    public static byte[] tryGetBitmapFromURL(String src) {
        java.net.URL url;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            url = new java.net.URL(src);
            connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            inputStream = connection.getInputStream();

            return readInputStreamToArray(inputStream, connection.getContentLength());

        } catch (IOException e) {
            Log.e(LOG_TAG, "File could not be downloaded", e);
            return null;
        } finally {
            if (connection != null)
                connection.disconnect();
            try {
                if (inputStream != null)
                    inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
