package com.glsebastiany.smartcatalogspl.di.components;



import com.glsebastiany.smartcatalogspl.di.modules.FragmentModule;
import com.glsebastiany.smartcatalogspl.di.scopes.PerFragment;
import com.glsebastiany.smartcatalogspl.ui.gallery.FragmentGallery;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = {FragmentModule.class})
public interface FragmentComponent {
    //void inject(BaseFragment baseFragment); // DO NOT INJECT ON BASE CLASSES
    //void inject(FragmentGallery fragmentGallery);
}
