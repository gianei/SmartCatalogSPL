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


import com.glsebastiany.ditlantaapp.data.FileServices;
import com.glsebastiany.ditlantaapp.data.imagefetching.ImageFetcherNotification;

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
