package com.glsebastiany.smartcatalogspl.di.components;

import android.support.v4.app.FragmentStatePagerAdapter;

import com.glsebastiany.smartcatalogspl.di.modules.FragmentModule;
import com.glsebastiany.smartcatalogspl.di.modules.PageAdapterModule;
import com.glsebastiany.smartcatalogspl.di.scopes.PerFragment;
import com.glsebastiany.smartcatalogspl.ui.gallery.FragmentGallery;

import dagger.Component;

@PerFragment
@Component(dependencies = GalleryComponent.class, modules = {FragmentModule.class, PageAdapterModule.class})
public interface GalleryFragmentComponent {
    void inject(FragmentGallery fragmentGallery);

    FragmentStatePagerAdapter fragmentStatePageAdapter();

}
