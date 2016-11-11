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

package com.glsebastiany.ditlantaapp.data.imagefetching;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.glsebastiany.ditlantaapp.data.imagefetching.servers.FetchFromCloudinary;
import com.glsebastiany.ditlantaapp.data.imagefetching.servers.FetchFromLan;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.Singletons;

public class ImageFetcherIntentService extends IntentService {

    private static final String LOG_TAG = "Image_Fetcher_Service";
    private static final String FETCH_TYPE_EXTRA = "fetchTypeExtra";

    public enum FetchType{
        LOCAL_LAN,
        CLOUDINARY
    }

    private FetchType fetchType;

    ItemBasicUseCases itemBasicUseCases;

    public static void startService(Context context, FetchType fetchType){
        Intent intent = new Intent(context, ImageFetcherIntentService.class);
        intent.putExtra(FETCH_TYPE_EXTRA, fetchType);
        context.startService(intent);
    }

    public ImageFetcherIntentService(){
        super("ImageFetcherServiceThread");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        itemBasicUseCases = Singletons.getInstance().itemBasicUseCases;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        fetchType = (FetchType) intent.getSerializableExtra(FETCH_TYPE_EXTRA);

        switch (fetchType){
            case CLOUDINARY:
                new FetchFromCloudinary(getApplicationContext()).fetch();
                return;
            case LOCAL_LAN:
                new FetchFromLan(getApplicationContext(), itemBasicUseCases).fetch();
                return;
        }
    }

}
