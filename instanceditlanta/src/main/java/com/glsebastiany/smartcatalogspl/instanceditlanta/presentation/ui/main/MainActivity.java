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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.glsebastiany.smartcatalogspl.core.Utils;
import com.glsebastiany.smartcatalogspl.core.nucleous.RequiresPresenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.main.MainActivityBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.main.MainAdapterBase;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.imagefetching.ImageFetcherIntentService;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.preferences.ActivityPreferences_;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.login.FirebaseAuthentication;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;

@EActivity(R.layout.activity_main)
@OptionsMenu({R.menu.menu_search, R.menu.menu_updates })
@RequiresPresenter(MainPresenter.class)
public class MainActivity extends MainActivityBase<MainPresenter> {

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
        ActivityPreferences_.intent(this).start();
    }

    @OptionsItem(R.id.menu_switch_lock_task)
    public void menuSwitchLock(){
        Utils.switchLockTasMode(this);
    }

    @AfterViews
    public void myAfterViews() {
        setupToolbar();
    }

    private void setupToolbar() {
        setupToolbarLogo();
        setSupportActionBar(toolbar);
    }

    private void setupToolbarLogo() {
        toolbar.setLogo(R.drawable.image_logo);
        toolbar.setLogoDescription(getString(R.string.menu_logo_description));
        toolbar.setTitle("");
    }



    private FirebaseAuthentication firebaseAuthentication;

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

    @Override
    public MainAdapterBase getAdapter(){
        return new MainAdapter(this, baseAppDisplayFactory);
    }

    @Override
    protected void injectMe(MainActivityBase<MainPresenter> mainActivityBase) {
        AndroidApplication.<ApplicationComponent>singleton().getApplicationComponent().inject(mainActivityBase);
    }

}
