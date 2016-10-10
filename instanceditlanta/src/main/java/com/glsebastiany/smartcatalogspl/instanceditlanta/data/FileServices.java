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

package com.glsebastiany.smartcatalogspl.instanceditlanta.data;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.preferences.SharedPreferencesFolder_;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class FileServices {

    private static final String LOG_TAG = FileServices.class.getName();


    public static void checkDestinationFolder(Context context) throws ImagesFolderException {
        if (!getFotosFolder(context).isDirectory()){
            Log.w(LOG_TAG, "Fotos folder will be created");
            if (!createFotosFolderDirectory(context)){
                Log.e(LOG_TAG, "Fotos folder could not be created");
                throw new ImagesFolderException();
            }
        }
    }

    protected static boolean createFotosFolderDirectory(Context context) {
        File folder = getFotosFolder(context);
        return folder.mkdir();
    }

    public static ArrayList<String> getPossibleFotosFolders(Context context){
        ArrayList<String> folders = new ArrayList<>();
        folders.add(getDefaultFotosFolder(context));

        File[] externalFilesDirs = context.getExternalFilesDirs(null);
        for (File dir: externalFilesDirs) {
            if (dir != null)
                if (!folders.contains(dir.getAbsolutePath() + getFotosContainerFolder(context)))
                    folders.add(dir.getAbsolutePath() + getFotosContainerFolder(context));

        };

        return folders;
    }

    @NonNull
    private static String getFotosContainerFolder(Context context) {
        return File.separator + context.getResources().getString(R.string.fotos_folder);
    }

    @NonNull
    public static void checkAndSetDefaultFotosFolder(Context context) {
        SharedPreferencesFolder_ sharedPreferencesFolder_ = new SharedPreferencesFolder_(context);
        if (sharedPreferencesFolder_.photosFolder().get().equals(""))
            sharedPreferencesFolder_.photosFolder().put(getDefaultFotosFolder(context));

    }

    @NonNull
    protected static String getDefaultFotosFolder(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return Environment.getExternalStorageDirectory().getAbsolutePath()
                        + getFotosContainerFolder(context);
            } else {
                File partPath = context.getExternalFilesDir(null);
                if (partPath!= null)
                    return partPath.getAbsolutePath() + getFotosContainerFolder(context);
                else
                    return context.getFilesDir().getAbsolutePath() + getFotosContainerFolder(context);
            }
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath()
                    + getFotosContainerFolder(context);
        }
    }

    @NonNull
    public static File getFotosFolder(Context context) {
        return new File(new SharedPreferencesFolder_(context).photosFolder().get());
    }

    public static String getFullImageUrlFromFileName(Context context, String imageUrl) {
        return getFotosFolder(context).getPath()
                + File.separator + imageUrl;
    }



    public static void tryWriteToLocalFile(byte[] contents, String imageCompleteUrl) {
        try {
            writeToLocalFile(imageCompleteUrl, contents);
            Log.d(LOG_TAG, "File " + imageCompleteUrl + " saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "File " + imageCompleteUrl + " could not be saved!", e);
        }
    }

    protected static void writeToLocalFile(String imageCompleteUrl, byte[] contents) throws IOException {
        try (
                BufferedOutputStream bufferedOutputStream =
                        new BufferedOutputStream(new FileOutputStream(imageCompleteUrl))
        ) {
            bufferedOutputStream.write(contents, 0, contents.length);
            bufferedOutputStream.flush();
        }
    }




    public static String getImageCompleteUrl() {
        return "http://res.cloudinary.com/smartcatalog/image/upload/v" + (new Date().getTime() / 1000) + "/ditlanta/items/";
    }


    public static class ImagesFolderException extends Exception {
        public ImagesFolderException(){
            super("Photos folder could not be created");
        }
    }
}
