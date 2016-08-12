package com.glsebastiany.smartcatalogspl.di.components;



import com.glsebastiany.smartcatalogspl.di.modules.ActivityModule;
import com.glsebastiany.smartcatalogspl.di.scopes.PerActivity;
import com.glsebastiany.smartcatalogspl.ui.ActivityMain;
import com.glsebastiany.smartcatalogspl.ui.gallery.ActivityGallery;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    //void inject(BaseActivity baseActivity); // DO NOT INJECT ON BASE CLASSES
    void inject(ActivityGallery activityGallery);
    void inject(ActivityMain activityMain);
}
