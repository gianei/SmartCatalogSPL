package com.glsebastiany.smartcatalogspl.di.components;



import com.glsebastiany.smartcatalogspl.di.modules.FragmentModule;
import com.glsebastiany.smartcatalogspl.di.scopes.PerFragment;
import com.glsebastiany.smartcatalogspl.ui.gallery.GalleryFragment;
import com.glsebastiany.smartcatalogspl.ui.gallery.grid.GalleryGridFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(GalleryGridFragment galleryGridFragment);
    void inject(GalleryFragment galleryFragment);
}
