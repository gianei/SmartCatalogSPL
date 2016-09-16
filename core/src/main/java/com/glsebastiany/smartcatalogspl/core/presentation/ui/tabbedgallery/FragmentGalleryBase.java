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

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseTabbedGalleryController;
import com.glsebastiany.smartcatalogspl.core.presentation.system.FragmentBase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

@EFragment
public abstract class FragmentGalleryBase extends FragmentBase {

    @Inject
    BaseTabbedGalleryController tabbedGalleryController;

    @ViewById(resName="pager")
    ViewPager viewPager;

    @ViewById(resName="progressBar")
    ProgressBar progressBar;

    @ViewById(resName="slidingTabLayout")
    TabLayout tabLayout;

    @ViewById(resName="left_drawer")
    ListView drawerListView;

    @ViewById(resName="drawer_layout")
    DrawerLayout drawerLayout;



    List<String> categoriesId;
    @FragmentArg
    void categoriesIdExtra(String[] categoriesIds){
        this.categoriesId = Arrays.asList(categoriesIds);
    }

    @Click(resName="drawerTriggerView")
    public void onDrawerTriggerClick(){
        if (drawerLayout.isDrawerOpen(drawerListView))
            drawerLayout.closeDrawer(drawerListView);
        else
            drawerLayout.openDrawer(drawerListView);
    }

    @Override
    protected void injectComponent() {
        injectMe(this);
    }

    protected abstract void injectMe(FragmentGalleryBase fragmentGalleryBase);

    @AfterViews
    public void afterViews() {
        setHasOptionsMenu(true);

        setupSlidingTabsAndViewPager();
        setupDrawer();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tabbedGalleryController.endSubscriptions();
    }

    private void setupSlidingTabsAndViewPager() {

        tabbedGalleryController.setupPager(getActivity(), progressBar, viewPager, categoriesId);

        tabbedGalleryController.setupSlidingTabs(tabLayout, viewPager);
    }

    private void setupDrawer() {

        tabbedGalleryController.setupDrawerAdapter(getActivity(), drawerListView);

    }
}
