package com.glsebastiany.smartcatalogspl.di.components;

import android.support.v7.widget.RecyclerView;

import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.di.BaseActivity;
import com.glsebastiany.smartcatalogspl.di.modules.GalleryModule;
import com.glsebastiany.smartcatalogspl.di.scopes.PerGallery;
import com.glsebastiany.smartcatalogspl.ui.gallery.grid.GalleryGridFragment;

import dagger.Component;
import rx.Observable;

@PerGallery
@Component(dependencies = ApplicationComponent.class, modules = {GalleryModule.class})
public interface GalleryComponent {
    void inject(GalleryGridFragment galleryGridFragment);

    Observable<ItemModel> itemsObservable();
    RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerViewAdapter();
}
