package com.glsebastiany.smartcatalogspl.di;


import android.app.Application;

import com.glsebastiany.smartcatalogspl.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.di.components.DaggerApplicationComponent;
import com.glsebastiany.smartcatalogspl.di.components.DaggerGalleryComponent;
import com.glsebastiany.smartcatalogspl.di.components.GalleryComponent;
import com.glsebastiany.smartcatalogspl.di.modules.ApplicationModule;
import com.glsebastiany.smartcatalogspl.di.modules.GalleryModule;

import rx.Observable;


public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    private GalleryComponent galleryComponent;

    @Override public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    /**
     * Used to mantain scope of Gallery that is bigger than the activity scope <br>
     * See more at <a href="http://frogermcs.github.io/dependency-injection-with-dagger-2-custom-scopes">CustomScopes</a>
     * @param itemsObservable
     */
    public GalleryComponent createGalleryComponent(Observable<ItemModel> itemsObservable, Observable<CategoryModel> categoriesObservable){
        galleryComponent = DaggerGalleryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .galleryModule(new GalleryModule(itemsObservable, categoriesObservable))
                .build();

        return galleryComponent;
    }

    public GalleryComponent getGalleryComponent(){
        return galleryComponent;
    }

    public void releaseGalleryComponent(){
        galleryComponent = null;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
