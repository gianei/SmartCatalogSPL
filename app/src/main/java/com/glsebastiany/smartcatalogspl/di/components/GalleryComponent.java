package com.glsebastiany.smartcatalogspl.di.components;

import android.support.v7.widget.RecyclerView;

import com.glsebastiany.smartcatalogspl.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.di.modules.GalleryModule;
import com.glsebastiany.smartcatalogspl.di.scopes.PerGallery;
import com.glsebastiany.smartcatalogspl.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.presentation.DisplayFactory;
import com.glsebastiany.smartcatalogspl.ui.gallery.grid.FragmentGalleryGrid;

import dagger.Component;
import rx.Observable;

@PerGallery
@Component(dependencies = ApplicationComponent.class, modules = {GalleryModule.class})
public interface GalleryComponent {
    void inject(FragmentGalleryGrid fragmentGalleryGrid);

    Observable<ItemModel> itemsObservable();
    Observable<CategoryModel> categoriesObservable();
    RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerViewAdapter();

    //these needs to be copied from ApplicationComponent to be accessible from dependent components
    DisplayFactory displayFactory();
    CategoryUseCases categoryUseCases();
}
