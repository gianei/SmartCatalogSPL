package com.glsebastiany.smartcatalogspl.instanceditlanta.data.imagefetching.servers;


import android.content.Context;
import android.util.Log;


import com.glsebastiany.smartcatalogspl.instanceditlanta.data.FileServices;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.imagefetching.ImageFetcherNotification;

import java.io.IOException;
import java.io.InputStream;

public abstract class FetchFrom {
    private static final String LOG_TAG = FetchFrom.class.getName();

    protected Context applicationContext;
    protected ImageFetcherNotification imageFetcherNotification;

    public FetchFrom(Context context){
        this.applicationContext = context.getApplicationContext();
        imageFetcherNotification = new ImageFetcherNotification(this.applicationContext);

    }

    public void fetch(){
        imageFetcherNotification.start();

        try {
            FileServices.checkDestinationFolder(applicationContext);
        } catch (FileServices.ImagesFolderException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.getMessage(), e);
            imageFetcherNotification.finalError();
        }


        fetchFromServer();

    }

    protected abstract void fetchFromServer();

    protected static byte[] readInputStreamToArray(InputStream inputStream, int totalSize) throws IOException {
        byte[] contents = new byte[totalSize];

        readFromInputStreamToArray(contents, inputStream, totalSize);

        return contents;
    }

    protected static void readFromInputStreamToArray(byte[] contents, InputStream inputStream, int totalSize) throws IOException {
        int bytesRead = 0;
        do {
            bytesRead += inputStream.read(contents, bytesRead, totalSize - bytesRead);
        } while (bytesRead != totalSize);
    }
}
