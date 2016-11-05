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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.search;

import android.app.SearchManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itemsets.grid.GalleryGridFragmentBase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

@EActivity(resName = "activity_search")
//@OptionsMenu(R.menu.menu_gallery)
public class SearchActivity extends AppCompatActivity {

    BaseAppDisplayFactory appDisplayFactory;

    @Extra(SearchManager.QUERY)
    String searchQuery;

    @InstanceState
    public boolean isFromSavedInstance = false;

    @ViewById(resName = "main_toolbar")
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
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @AfterViews
    public void afterViews(){
        setupToolbar();

        if (!isFromSavedInstance) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            GalleryGridFragmentBase galleryGridFragment = (GalleryGridFragmentBase) appDisplayFactory.provideItemSetFragment(searchQuery, false);

            fragmentTransaction.add(R.id.main_fragment_container, galleryGridFragment);
            fragmentTransaction.commit();

            isFromSavedInstance = true;
        }
    }


}
