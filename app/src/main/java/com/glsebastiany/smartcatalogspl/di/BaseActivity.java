/*
 *     SmartCatalogSPL, an Android catalog Software Product Line
 *     Copyright (c) 2016 Gianei Leandro Sebastiany
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.glsebastiany.smartcatalogspl.di;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.di.components.activity.ActivityComponent;
import com.glsebastiany.smartcatalogspl.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.di.components.activity.DaggerActivityComponent;
import com.glsebastiany.smartcatalogspl.di.modules.ActivityModule;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.login.FirebaseAuthentication;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {

    protected ActivityComponent activityComponent;

    @Inject
    BaseAppDisplayFactory baseAppDisplayFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();

        activityComponent.inject(this);

        this.initializeInjector();

        setupOther();
    }

    public AndroidApplication getAndroidApplication(){
        return (AndroidApplication)getApplication();
    }

    public ApplicationComponent getApplicationComponent() {
        return getAndroidApplication().getApplicationComponent();
    }

    public ActivityComponent getActivityComponent(){
        return activityComponent;
    }


    protected abstract void initializeInjector();





    //** SPECIFIC CODE REGION **//
    private FirebaseAuthentication firebaseAuthentication;


    private void setupOther() {
        firebaseAuthentication = new FirebaseAuthentication(this, baseAppDisplayFactory);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuthentication.setOnPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuthentication.setOnResume();
    }
    //** END SPECIFIC CODE REGION **//
}
