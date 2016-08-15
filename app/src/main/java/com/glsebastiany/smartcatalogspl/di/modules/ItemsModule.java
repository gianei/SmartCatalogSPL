package com.glsebastiany.smartcatalogspl.di.modules;

import android.support.v7.widget.RecyclerView;

import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.di.scopes.PerItemsGallery;
import com.glsebastiany.smartcatalogspl.presentation.GalleryGridItemsAdapter;

import dagger.Module;
import dagger.Provides;
import rx.Observable;

@Module
public class ItemsModule {
    /*private final Observable<ItemModel> itemsObservable;

    public ItemsModule(Observable<ItemModel> itemsObservable) {
        this.itemsObservable = itemsObservable;
    }

    @Provides
    @PerItemsGallery
    Observable<ItemModel> provideItemsObservable() {
        return this.itemsObservable;
    }*/

    @Provides
    @PerItemsGallery
    RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerViewAdapter(GalleryGridItemsAdapter galleryGridItemsAdapter){

        return galleryGridItemsAdapter;
    }
}
