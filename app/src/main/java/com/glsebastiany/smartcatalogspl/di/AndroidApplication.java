package com.glsebastiany.smartcatalogspl.di;


import android.app.Application;

import com.glsebastiany.smartcatalogspl.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.di.components.DaggerItemsGalleryComponent;
import com.glsebastiany.smartcatalogspl.di.components.ItemsGalleryComponent;
import com.glsebastiany.smartcatalogspl.di.components.DaggerApplicationComponent;
import com.glsebastiany.smartcatalogspl.di.modules.ApplicationModule;
import com.glsebastiany.smartcatalogspl.di.modules.CategoriesModule;
import com.glsebastiany.smartcatalogspl.di.modules.ItemsModule;

import rx.Observable;


public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    private ItemsGalleryComponent itemsGalleryComponent;

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
     */
    public ItemsGalleryComponent createItemsGalleryComponent(){
        itemsGalleryComponent = DaggerItemsGalleryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .itemsModule(new ItemsModule())
                .build();

        return itemsGalleryComponent;
    }

    public ItemsGalleryComponent getItemsGalleryComponent(){
        return itemsGalleryComponent;
    }

    public void releaseGalleryComponent(){
        itemsGalleryComponent = null;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
