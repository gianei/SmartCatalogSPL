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

package com.glsebastiany.smartcatalogspl.instanceditlanta.data.imagefetching.servers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.FileServices;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.Item;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class FetchFromLan extends FetchFrom {

    private static final String LOG_TAG = FetchFromLan.class.getName();

    private final ItemUseCases itemUseCases;

    public FetchFromLan(Context context, ItemUseCases itemUseCases){
        super(context);
        this.itemUseCases = itemUseCases;
    }

    public void fetchFromServer() {

        try ( //Try with resources automatically closes streams
              Socket socket = connectToServer();
              DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
              DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        ) {
            imageFetcherNotification.informStatus();

            List<ItemModel> fetchItems = itemUseCases.getAll().toList().toBlocking().single();

            informRequestedImagesQuantity(outputStream, fetchItems.size());

            for (ItemModel fetchItem : fetchItems) {
                informNeededImageToServer(outputStream, (Item) fetchItem);
            }
            outputStream.flush();

            imageFetcherNotification.setNeededImages(readReceiveFilesQuantity(inputStream));

            for (ItemModel fetchItem : fetchItems) {
                if (!isServerSendingFile(inputStream)) {
                    continue;
                }

                int fileSize = getFileSizeFromServer(inputStream);

                byte[] buffer = readInputStreamToArray(inputStream, fileSize);

                FileServices.tryWriteToLocalFile(buffer, FileServices.getFullImageUrlFromFileName(applicationContext, ((Item)fetchItem).getImageUrl() + ".jpg"));

                imageFetcherNotification.increaseProcessedImages();
                imageFetcherNotification.informStatus();
            }

            imageFetcherNotification.finalSucess();

        } catch (ConnectException e) {
            Log.e(LOG_TAG, "It was not possible to establish connection with server!", e);
            imageFetcherNotification.finalError();
        } catch (InterruptedIOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Connection interrupted!", e);
            imageFetcherNotification.finalError();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "I/O error!", e);
            imageFetcherNotification.finalError();
        }


    }

    private Socket connectToServer() throws IOException {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress(preferences.getString(
                applicationContext.getString(R.string.pref_server_address_key),
                applicationContext.getString(R.string.pref_server_address_default)), 65432);
        socket.connect(address, 1000);
        Log.d(LOG_TAG, "Socket created");
        return socket;
    }

    private void informRequestedImagesQuantity(DataOutputStream outputStream, int quantity) throws IOException {
        outputStream.writeInt(quantity);
    }

    private void informNeededImageToServer(DataOutputStream outputStream, Item fetchItem) throws IOException {
        outputStream.writeUTF(fetchItem.getImageUrl() + ".jpg");

        String imageFullUrl = FileServices.getFullImageUrlFromFileName(applicationContext, fetchItem.getImageUrl() + ".jpg");
        File file = new File(imageFullUrl);
        outputStream.writeLong(file.lastModified());

        Log.d(LOG_TAG, "Image name and update time sent.");
    }

    private int readReceiveFilesQuantity(DataInputStream inputStream) throws IOException {
        return inputStream.readInt();
    }

    private boolean isServerSendingFile(DataInputStream inputStream) throws IOException {
        boolean isSending = inputStream.readBoolean();
        if (!isSending) {
            Log.d(LOG_TAG, "File is not present in server or is already up to date. Skiping.");
            return false;
        } else {
            Log.d(LOG_TAG, "File will be send.");
            return true;
        }
    }

    private int getFileSizeFromServer(DataInputStream inputStream) throws IOException {
        return inputStream.readInt();
    }
}
