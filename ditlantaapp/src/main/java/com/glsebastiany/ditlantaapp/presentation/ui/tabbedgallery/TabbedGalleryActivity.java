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

package com.glsebastiany.ditlantaapp.presentation.ui.tabbedgallery;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.Utils;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryActivityBase;
import com.glsebastiany.ditlantaapp.R;
import com.glsebastiany.ditlantaapp.data.imagefetching.ImageFetcherIntentService;
import com.glsebastiany.ditlantaapp.data.preferences.ActivityPreferences_;
import com.glsebastiany.ditlantaapp.presentation.di.AndroidApplication;
import com.glsebastiany.ditlantaapp.presentation.di.components.ApplicationComponent;
import com.glsebastiany.ditlantaapp.presentation.ui.login.FirebaseAuthentication;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;

import java.util.List;

@EActivity(R.layout.activity_gallery)
@OptionsMenu({R.menu.menu_gallery, R.menu.menu_updates, R.menu.menu_search })
public class TabbedGalleryActivity extends TabbedGalleryActivityBase {

    @Override
    protected void injectMe(TabbedGalleryActivityBase tabbedGalleryBaseActivity) {
        AndroidApplication.<ApplicationComponent>singleton().getApplicationComponent().inject(tabbedGalleryBaseActivity);
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
    void myAfterViews(){
        setupToolbar();
    }

    private void setupToolbar() {
        setupToolbarLogo();
        setSupportActionBar(toolbar);
        setupToolbarNavigation();
    }

    private void setupToolbarLogo() {
        toolbar.setLogo(R.drawable.image_logo);
        toolbar.setLogoDescription(getString(R.string.menu_logo_description));
        toolbar.setTitle("");
    }

    private void setupToolbarNavigation() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPress = System.currentTimeMillis();
                onBackPressed();
            }
        });
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