package com.glsebastiany.smartcatalogspl.di.components;

import android.content.Context;

import com.glsebastiany.smartcatalogspl.di.BaseActivity;
import com.glsebastiany.smartcatalogspl.data.CategoryRepository;
import com.glsebastiany.smartcatalogspl.di.modules.ApplicationModule;
import com.glsebastiany.smartcatalogspl.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.ui.ActivityMain;
import com.glsebastiany.smartcatalogspl.data.ItemRepository;
import com.glsebastiany.smartcatalogspl.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.presentation.DisplayFactory;
import com.glsebastiany.smartcatalogspl.ui.gallery.ActivityGallery;
import com.glsebastiany.smartcatalogspl.ui.gallery.GalleryFragment;
import com.glsebastiany.smartcatalogspl.ui.gallery.grid.GalleryGridFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(ActivityGallery activityGallery);
    void inject(ActivityMain activityMain);
    void inject(GalleryFragment galleryFragment);

    //Exposed to sub-graphs.
    Context context();
    ItemRepository itemRepository();
    ItemUseCases useCases();
    CategoryRepository categoryRepository();
    CategoryUseCases categoryUseCases();

    DisplayFactory displayFactory();

}
