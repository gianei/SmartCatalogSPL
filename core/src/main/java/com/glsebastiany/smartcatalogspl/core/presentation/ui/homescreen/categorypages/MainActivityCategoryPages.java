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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.homescreen.categorypages;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.RequiresPresenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.homescreen.MainActivityBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryPageAdapter;

@RequiresPresenter(MainActivityCategoryPagesPresenter.class)
public class MainActivityCategoryPages extends MainActivityBase<MainActivityCategoryPagesPresenter> {

    public TabLayout tabLayout;

    public ViewPager viewPager;

    private TabbedGalleryPageAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_tabbed);
        super.onCreate(savedInstanceState);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.slidingTabLayout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        afterViews();
    }


    public void afterViews(){
        setSupportActionBar(toolbar);

        pagerAdapter = new TabbedGalleryPageAdapter(getSupportFragmentManager(), baseAppDisplayFactory);

        presenterAfterView();

        setupToolbar();

        setupPager();
    }

    private void setupPager(){
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }


    public void stopLoading() {
        progressBar.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
    }

    public void addItem(CategoryModel suitCase) {
        pagerAdapter.addItem(suitCase);
    }

}
