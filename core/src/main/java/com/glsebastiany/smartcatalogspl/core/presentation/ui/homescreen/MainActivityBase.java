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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.homescreen;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.MvpRxActivityBase;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.Presenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.login.FirebaseAuthentication;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.Singletons;


public abstract class MainActivityBase<P extends Presenter> extends MvpRxActivityBase<P> {
    public ProgressBar progressBar;
    public Toolbar toolbar;
    public BaseAppDisplayFactory baseAppDisplayFactory;
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
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        baseAppDisplayFactory = Singletons.getInstance().baseAppDisplayFactory;

        firebaseAuthentication = new FirebaseAuthentication(this, baseAppDisplayFactory);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        baseAppDisplayFactory.configureToolbarLogo(this, toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        setupSearchMenu(searchMenuItem);

        if (baseAppDisplayFactory.inflateMenus(this, menu))
            return true;

        return super.onCreateOptionsMenu(menu);
    }

    void setupSearchMenu(MenuItem searchMenuItem){
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (baseAppDisplayFactory.menuSelected(this, item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    public void stopLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
