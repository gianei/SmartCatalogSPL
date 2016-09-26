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

import android.Manifest;
import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.glsebastiany.smartcatalogspl.core.Utils;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.ActivityMainBase;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.imagefetching.ImageFetcherIntentService;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.preferences.ActivityPreferences_;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ActivityComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.DaggerActivityComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.modules.ActivityModule;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.login.FirebaseAuthentication;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;

@EActivity(R.layout.activity_main)
@OptionsMenu({R.menu.menu_search, R.menu.menu_updates })
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

    @OptionsMenuItem(R.id.menu_search)
    void singleInjection(MenuItem searchMenuItem){
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }


    @OptionsItem(R.id.menu_update_images)
    public void menuUpdateImage(){
        ImageFetcherIntentService.startService(this, ImageFetcherIntentService.FetchType.CLOUDINARY);
    }

    @OptionsItem(R.id.menu_update_images_locally)
    public void menuUpdateImageLocally(){
        ImageFetcherIntentService.startService(this, ImageFetcherIntentService.FetchType.LOCAL_LAN);
    }

    @OptionsItem(R.id.menu_settings)
    public void menuSettings(){

        Intent intent = ActivityPreferences_.intent(this).get();
        startActivity(intent);
    }

    @OptionsItem(R.id.menu_switch_lock_task)
    public void menuSwitchLock(){
        Utils.switchLockTasMode(this);
    }

    @AfterViews
    public void myAfterViews() {
        verifyPermission();
    }


    private void verifyPermission() {
        // Should we show an explanation?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle(R.string.write_external_storage_request_title)
                            .setMessage(R.string.write_external_storage_request_message)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @TargetApi(Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    requestPermissions(
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            0);
                                }
                            });

                    builder.create().show();

                } else {
                    requestPermissions(
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            0);
                }
            }
        }
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
