package com.glsebastiany.smartcatalogspl.di.components;

import android.support.v4.app.FragmentStatePagerAdapter;

import com.glsebastiany.smartcatalogspl.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.di.modules.CategoriesModule;
import com.glsebastiany.smartcatalogspl.di.modules.FragmentModule;
import com.glsebastiany.smartcatalogspl.di.modules.PageAdapterModule;
import com.glsebastiany.smartcatalogspl.di.scopes.PerFragment;
import com.glsebastiany.smartcatalogspl.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.ui.gallery.FragmentGallery;

import dagger.Component;
import rx.Observable;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = {FragmentModule.class, PageAdapterModule.class})
public interface GalleryCategoriesComponent {
    void inject(FragmentGallery fragmentGallery);

    CategoryUseCases categoryUseCases();
    FragmentStatePagerAdapter fragmentStatePageAdapter();

}
