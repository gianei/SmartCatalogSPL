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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.main;

import android.os.Bundle;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.ActivityMainBase;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ActivityComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.DaggerActivityComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.modules.ActivityModule;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.login.FirebaseAuthentication;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class ActivityMain extends ActivityMainBase {
    ActivityComponent activityComponent;

    @Override
    protected void injectMe(ActivityMainBase activityMain) {
        activityComponent.inject(activityMain);
    }

    @Override
    protected void setupComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication)getApplication()).getApplicationComponent();
    }

    private FirebaseAuthentication firebaseAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}
