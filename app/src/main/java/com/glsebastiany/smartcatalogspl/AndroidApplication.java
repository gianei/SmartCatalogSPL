package com.glsebastiany.smartcatalogspl;


import android.app.Application;

import com.glsebastiany.smartcatalogspl.di.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.di.ApplicationModule;
import com.glsebastiany.smartcatalogspl.di.DaggerApplicationComponent;

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

}
