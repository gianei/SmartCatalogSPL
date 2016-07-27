package com.glsebastiany.smartcatalogspl.di;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.glsebastiany.smartcatalogspl.di.components.ActivityComponent;
import com.glsebastiany.smartcatalogspl.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.di.components.DaggerActivityComponent;
import com.glsebastiany.smartcatalogspl.di.modules.ActivityModule;


public abstract class BaseActivity extends AppCompatActivity {

    ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initializeInjector();

    }

    public ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication)getApplication()).getApplicationComponent();
    }

    public ActivityComponent getActivityComponent(){
        return activityComponent;
    }


    private void initializeInjector() {
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();

        activityComponent.inject(this);
    }
}
