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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.search;

import android.app.SearchManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.di.InjectableActivity;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.grid.GalleryGridFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EActivity(R.layout.activity_search)
@OptionsMenu(R.menu.menu_gallery)
public class SearchActivity extends InjectableActivity {

    @Inject
    BaseAppDisplayFactory appDisplayFactory;

    @Override
    protected void injectApplicationComponent() {
        AndroidApplication.<ApplicationComponent>singleton().getApplicationComponent().inject(this);
    }

    @Extra(SearchManager.QUERY)
    String searchQuery;

    @InstanceState
    public boolean isFromSavedInstance = false;

    @ViewById(R.id.main_toolbar)
    Toolbar toolbar;

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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @AfterViews
    public void afterViews(){
        setupToolbar();

        if (!isFromSavedInstance) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            GalleryGridFragment galleryGridFragment = (GalleryGridFragment) appDisplayFactory.provideGalleryGridFragment(searchQuery, false);

            fragmentTransaction.add(R.id.main_fragment_container, galleryGridFragment);
            fragmentTransaction.commit();

            isFromSavedInstance = true;
        }
    }


}
