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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.data.categorygroup.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.MvpRxActivityBase;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.RequiresPresenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.Utils;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.login.FirebaseAuthentication;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.splash.SplashScreenBase;

@RequiresPresenter(MainPresenterBase.class)
public class MainActivityBase extends MvpRxActivityBase<MainPresenterBase> {

    public ProgressBar progressBar;

    public RecyclerView recyclerView;

    public Toolbar toolbar;

    public BaseAppDisplayFactory baseAppDisplayFactory;

    protected MainAdapterBase mainAdapter;

    private FirebaseAuthentication firebaseAuthentication;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseAppDisplayFactory = SplashScreenBase.getInstance().baseAppDisplayFactory;

        firebaseAuthentication = new FirebaseAuthentication(this, baseAppDisplayFactory);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        afterViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        menuInflater.inflate(R.menu.menu_settings, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        setupSearchMenu(searchMenuItem);

        return super.onCreateOptionsMenu(menu);
    }

    void setupSearchMenu(MenuItem searchMenuItem){
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId_ = item.getItemId();
        if (itemId_ == R.id.menu_settings) {
            //ActivityPreferences_.intent(this).start();
            return true;
        }
        if (itemId_ == R.id.menu_switch_lock_task) {
            Utils.switchLockTasMode(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void afterViews(){
        setSupportActionBar(toolbar);

        mainAdapter = getAdapter();

        recyclerView.setLayoutManager(
                new GridLayoutManager(
                        MainActivityBase.this,
                        MainActivityBase.this.getResources().getInteger(com.glsebastiany.smartcatalogspl.core.R.integer.grid_span_start),
                        LinearLayoutManager.VERTICAL, false
                )
        );

        recyclerView.setAdapter(mainAdapter);

        presenterAfterView();

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

    public void stopLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void addItem(CategoryGroupModel suitCase) {
        mainAdapter.addItem(suitCase);
    }


    public MainAdapterBase getAdapter(){
        return new MainAdapterBase(this, baseAppDisplayFactory);
    }

}
