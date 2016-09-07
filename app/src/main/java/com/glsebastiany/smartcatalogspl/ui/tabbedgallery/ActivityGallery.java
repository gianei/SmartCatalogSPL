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

package com.glsebastiany.smartcatalogspl.ui.tabbedgallery;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.di.BaseActivity;
import com.glsebastiany.smartcatalogspl.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.domain.ItemUseCases;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

@EActivity(R.layout.activity_gallery)
public class ActivityGallery extends BaseActivity {

    @InstanceState
    boolean fromSavedInstance = false;

    private Fragment galleryFragment;

    private long lastPress;
    private Toast toast;

    @Inject
    ItemUseCases itemUseCases;

    @Inject
    CategoryUseCases categoryUseCases;

    @ViewById(R.id.main_toolbar)
    Toolbar toolbar;

    @AfterViews
    protected void afterViews() {

        setupToolbar();
        setupGalleryFragment();

    }


    private void setupToolbar() {
        setupToolbarLogo(toolbar);
        setSupportActionBar(toolbar);
        //setupToolbarNavigation();
    }

    private void setupToolbarLogo(Toolbar toolbar) {
        //toolbar.setLogo(R.drawable.image_logo);
        toolbar.setLogoDescription(getString(R.string.app_name));
        toolbar.setTitle(getString(R.string.app_name));
    }

    private void setupToolbarNavigation() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }


    private void setupGalleryFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        galleryFragment = FragmentGallery_.builder().build();
        fragmentTransaction.add(R.id.main_fragment_container, galleryFragment, FragmentGallery_.TAG);
        fragmentTransaction.commit();


    }

    @Override
    public void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }


    /**
     * Workaround for child fragments backstack
        based on http://stackoverflow.com/a/24176614
     * @param fragmentManager
     * @return true when backStack is popped
     */
    private boolean depthFirstOnBackPressed(FragmentManager fragmentManager) {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if (fragmentList != null && fragmentList.size() > 0) {
            for (Fragment fragment : fragmentList) {
                if (fragment == null) {
                    continue;
                }
                if (fragment.isVisible()) {
                    if (fragment.getChildFragmentManager() != null) {
                        if (depthFirstOnBackPressed(fragment.getChildFragmentManager())) {
                            return true;
                        }
                    }
                }
            }
        }

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (depthFirstOnBackPressed(fm)) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        //Toast.LENGTH_SHORT is 2000
        if(currentTime - lastPress > 2000){
            toast = Toast.makeText(getBaseContext(), "Back sure?", Toast.LENGTH_SHORT);
            toast.show();
            lastPress = currentTime;
        }else{
            if (toast != null){
                toast.cancel();
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void initializeInjector() {
        getActivityComponent().inject(this);

        /*if (!fromSavedInstance){
            getAndroidApplication().createItemsGalleryComponent(itemUseCases.allItems(), categoryUseCases.mainViewCategories());
            fromSavedInstance = true;
        }*/

    }

}