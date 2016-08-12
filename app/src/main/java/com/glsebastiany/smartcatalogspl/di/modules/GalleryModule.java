package com.glsebastiany.smartcatalogspl.di.modules;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.glsebastiany.smartcatalogspl.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.di.scopes.PerGallery;
import com.glsebastiany.smartcatalogspl.presentation.GalleryGridItemsAdapter;

import dagger.Module;
import dagger.Provides;
import rx.Observable;

@Module
public class GalleryModule {
    private final Observable<ItemModel> itemsObservable;
    private final Observable<CategoryModel> categoriesObservable;

    public GalleryModule(Observable<ItemModel> itemsObservable, Observable<CategoryModel> categoriesObservable) {
        Log.d(GalleryModule.class.getName(), "Adapter created");
        this.itemsObservable = itemsObservable;
        this.categoriesObservable = categoriesObservable;
    }

    @Provides
    @PerGallery
    Observable<CategoryModel> provideCategoryObservable(){
        return this.categoriesObservable;
    }

    @Provides
    @PerGallery
    Observable<ItemModel> provideItemsObservable() {
        return this.itemsObservable;
    }

    @Provides
    @PerGallery
    RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerViewAdapter(GalleryGridItemsAdapter galleryGridItemsAdapter){

        return galleryGridItemsAdapter;
    }
}
