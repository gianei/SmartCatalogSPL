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

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.di.BaseFragment;
import com.glsebastiany.smartcatalogspl.presentationfood.tabbedgallery.TabbedGalleryController;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(R.layout.fragment_gallery)
public class FragmentGallery extends BaseFragment {

    public static final String TAG = "galleryFragment";

    @Inject
    TabbedGalleryController tabbedGalleryController;

    @Click(R.id.drawerTriggerView)
    public void onDrawerTriggerClick(){
        if (drawerLayout.isDrawerOpen(drawerListView))
            drawerLayout.closeDrawer(drawerListView);
        else
            drawerLayout.openDrawer(drawerListView);
    }

    @ViewById(R.id.pager)
    ViewPager viewPager;

    @ViewById(R.id.progressBar)
    ProgressBar progressBar;

    @ViewById(R.id.slidingTabLayout)
    TabLayout tabLayout;

    @ViewById(R.id.left_drawer)
    ListView drawerListView;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @Override
    protected void initializeInjector() {
        getFragmentComponent().inject(this);
    }

    @AfterViews
    public void afterViews() {
        setHasOptionsMenu(true);

        setupSlidingTabsAndViewPager();
        setupDrawer();

    }


    private void setupSlidingTabsAndViewPager() {

        tabbedGalleryController.setupPager(getActivity(), progressBar, viewPager);

        tabbedGalleryController.setupSlidingTabs(tabLayout, viewPager);
    }


    private void setupDrawer() {

        tabbedGalleryController.setupDrawerAdapter(getActivity(), drawerListView);

    };


}
