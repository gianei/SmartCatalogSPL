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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.Singletons;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.SingletonsExtended;

import java.util.List;

import static com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.Utils.depthFirstOnBackPressed;

public class TabbedGalleryActivityBase extends AppCompatActivity {

    public final static String CATEGORIES_IDS_EXTRA = "categoriesIds";
    private final static String IS_SAVED_FROM_INSTANCE_BUNDLE = "isFromSavedInstance";

    protected ItemBasicUseCases itemBasicUseCases;

    protected CategoryUseCases categoryUseCases;

    protected BaseAppDisplayFactory appDisplayFactory;

    public String[] categoriesIds;

    public boolean isFromSavedInstance = false;

    public Toolbar toolbar;

    protected long lastPress;
    private Toast toast;

    public static void start(Context context, List<String> categoriesIds ){
        Intent intent = new Intent(context, TabbedGalleryActivityBase.class);
        intent.putExtra(CATEGORIES_IDS_EXTRA, categoriesIds.toArray(new String[categoriesIds.size()]));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);


        Bundle extras_ = getIntent().getExtras();
        if (extras_!= null) {
            if (extras_.containsKey(CATEGORIES_IDS_EXTRA)) {
                this.categoriesIds = extras_.getStringArray(CATEGORIES_IDS_EXTRA);
            }
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        afterViews();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle_) {
        super.onSaveInstanceState(bundle_);
        bundle_.putBoolean(IS_SAVED_FROM_INSTANCE_BUNDLE, isFromSavedInstance);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        isFromSavedInstance = savedInstanceState.getBoolean(IS_SAVED_FROM_INSTANCE_BUNDLE);
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void afterViews() {


        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                v -> {
                    lastPress = System.currentTimeMillis();
                    ((AppCompatActivity)v.getContext()).onBackPressed();
                }
        );

        itemBasicUseCases = Singletons.getInstance().itemBasicUseCases;
        categoryUseCases = SingletonsExtended.getInstance().categoryUseCases;
        appDisplayFactory = Singletons.getInstance().baseAppDisplayFactory;

        appDisplayFactory.configureToolbarLogo(this, toolbar);

        setupGalleryFragment();
    }


    private void setupGalleryFragment() {

        if (!isFromSavedInstance) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Fragment galleryFragment = TabbedGalleryFragment.newInstance(categoriesIds);
            fragmentTransaction.add(R.id.main_fragment_container, galleryFragment);
            fragmentTransaction.commit();

            isFromSavedInstance = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_gallery, menu);
        menuInflater.inflate(R.menu.menu_search, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        setupSearchMenu(searchMenuItem);

        appDisplayFactory.inflateMenus(this, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (appDisplayFactory.menuSelected(this, item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void setupSearchMenu(MenuItem searchMenuItem){
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    @Override
    public void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (depthFirstOnBackPressed(getSupportFragmentManager())) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        //Toast.LENGTH_SHORT is 2000
        if(currentTime - lastPress > 2000){
            toast = Toast.makeText(getBaseContext(), R.string.confirm_back_toast, Toast.LENGTH_SHORT);
            toast.show();
            lastPress = currentTime;
        }else{
            if (toast != null){
                toast.cancel();
            }
            super.onBackPressed();
        }
    }
}
