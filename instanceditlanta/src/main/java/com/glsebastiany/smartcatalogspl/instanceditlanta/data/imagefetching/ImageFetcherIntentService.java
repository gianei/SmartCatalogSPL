package com.glsebastiany.smartcatalogspl.instanceditlanta.data.imagefetching;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.imagefetching.servers.FetchFromCloudinary;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.imagefetching.servers.FetchFromLan;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;

import javax.inject.Inject;

public class ImageFetcherIntentService extends IntentService {

    private static final String LOG_TAG = "Image_Fetcher_Service";
    private static final String FETCH_TYPE_EXTRA = "fetchTypeExtra";

    public enum FetchType{
        LOCAL_LAN,
        CLOUDINARY
    }

    private FetchType fetchType;

    @Inject
    ItemUseCases itemUseCases;

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
        ((AndroidApplication)getApplication()).getApplicationComponent().inject(this);
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
                new FetchFromLan(getApplicationContext(), itemUseCases).fetch();
                return;
        }
    }

}
