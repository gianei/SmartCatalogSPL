package com.glsebastiany.smartcatalogspl.di.components;

import android.support.v7.widget.RecyclerView;

import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.di.modules.CategoriesModule;
import com.glsebastiany.smartcatalogspl.di.modules.ItemsModule;
import com.glsebastiany.smartcatalogspl.di.scopes.PerItemsGallery;
import com.glsebastiany.smartcatalogspl.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.presentation.DisplayFactory;
import com.glsebastiany.smartcatalogspl.ui.gallery.grid.FragmentGalleryGrid;

import dagger.Component;
import rx.Observable;

@PerItemsGallery
@Component(dependencies = ApplicationComponent.class, modules = {ItemsModule.class})
public interface ItemsGalleryComponent {
    void inject(FragmentGalleryGrid fragmentGalleryGrid);

    //Observable<ItemModel> itemsObservable();
    RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerViewAdapter();

    //these needs to be copied from ApplicationComponent to be accessible from dependent components
    DisplayFactory displayFactory();
    CategoryUseCases categoryUseCases();
}
