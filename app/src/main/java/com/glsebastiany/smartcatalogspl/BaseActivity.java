package com.glsebastiany.smartcatalogspl;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.glsebastiany.smartcatalogspl.di.ApplicationComponent;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication)getApplication()).getApplicationComponent();
    }
}
