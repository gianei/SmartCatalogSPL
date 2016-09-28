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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.tabbedgallery;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryBaseActivity;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ActivityComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.DaggerActivityComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.modules.ActivityModule;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.login.FirebaseAuthentication;

import org.androidannotations.annotations.EActivity;

import java.util.List;

@EActivity(R.layout.activity_gallery)
public class TabbedGalleryActivity extends TabbedGalleryBaseActivity {

    ActivityComponent activityComponent;

    @Override
    protected void setupComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void injectMe(TabbedGalleryBaseActivity tabbedGalleryBaseActivity) {
        activityComponent.inject(tabbedGalleryBaseActivity);
    }

    public ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication)getApplication()).getApplicationComponent();
    }

    public static void start(Context context, List<String> categoriesIds ){
        TabbedGalleryActivity_
                .intent(context)
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .categoriesIds(categoriesIds.toArray(new String[categoriesIds.size()]))
                .start();
    }

    private FirebaseAuthentication firebaseAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuthentication = new FirebaseAuthentication(this, appDisplayFactory);
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