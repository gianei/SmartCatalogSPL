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

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.MvpRxFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.RequiresPresenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.splash.SplashScreenBase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

@EFragment(resName = "fragment_gallery")
@RequiresPresenter(TabbedGalleryPresenterBase.class)
public class TabbedGalleryFragment extends MvpRxFragmentBase<TabbedGalleryPresenterBase> {

    protected BaseAppDisplayFactory baseAppDisplayFactory;

    TabbedGalleryDrawerAdapter drawerAdapter;
    TabbedGalleryPageAdapter pagerAdapter;

    @ViewById(resName="pager")
    public ViewPager viewPager;

    @ViewById(resName="progressBar")
    public ProgressBar progressBar;

    @ViewById(resName="slidingTabLayout")
    public TabLayout tabLayout;

    @ViewById(resName="left_drawer")
    public ListView drawerListView;

    @ViewById(resName="drawer_layout")
    public DrawerLayout drawerLayout;

    @ViewById(resName="drawerTriggerView")
    public Button drawerButton;

    @InstanceState
    public int drawerButtonUnselectedColor;

    @InstanceState
    public String[] categoriesId;

    @InstanceState
    public int selectedTabId = 0;

    @FragmentArg
    public void categoriesIdExtra(String[] categoriesIds){
        this.categoriesId = categoriesIds;
    }

    @Click(resName="drawerTriggerView")
    public void onDrawerTriggerClick(){
        if (drawerLayout.isDrawerOpen(drawerListView))
            drawerLayout.closeDrawer(drawerListView);
        else
            drawerLayout.openDrawer(drawerListView);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        baseAppDisplayFactory = SplashScreenBase.getInstance().baseAppDisplayFactory;
    }

    @AfterViews
    public void afterViews() {
        setHasOptionsMenu(true);
        setupPager();
        setupSlidingTabs();
        setupDrawerButton();

        presenterAfterViews();
    }

    private void setupPager(){

        pagerAdapter = createPagerAdapter();

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                selectedTabId = position;
                setupDrawerAdapter();
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setupSlidingTabs(){
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    public void setupDrawerAdapter() {
        CategoryModel currentTabCategory = pagerAdapter.getCategoryModel(viewPager.getCurrentItem());
        drawerAdapter = createDrawerAdapter(currentTabCategory);
        drawerListView.setAdapter(drawerAdapter);
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
        presenterFindDrawerCategories(currentTabCategory);

    }

    private void setupDrawerButton() {
        if (drawerLayout.isDrawerOpen(drawerListView)){
            drawerButton.setTextColor(Color.WHITE);
        }
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerButtonUnselectedColor = drawerButton.getCurrentTextColor();
                drawerButton.setTextColor(Color.WHITE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerButton.setTextColor(drawerButtonUnselectedColor);
            }

            @Override
            public void onDrawerStateChanged(int newState) {}
        });
    }

    public static TabbedGalleryFragment newInstance(String[] categoriesId){
        return TabbedGalleryFragment_.builder().categoriesIdExtra(categoriesId).build();
    }

    @NonNull
    protected TabbedGalleryPageAdapter createPagerAdapter() {
        return new TabbedGalleryPageAdapter(getFragmentManager(), baseAppDisplayFactory);
    }

    @NonNull
    protected TabbedGalleryDrawerAdapter createDrawerAdapter(CategoryModel categoryModel) {
        return new TabbedGalleryDrawerAdapter( categoryModel, getContext());
    }

    protected void presenterFindDrawerCategories(CategoryModel categoryModel) {
        getPresenter().findDrawerCategories(categoryModel);
    }

    public void stopLoading() {
        progressBar.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
    }

    public void addPageItem(CategoryModel categoryModel){
        pagerAdapter.addItem(categoryModel);
        if (pagerAdapter.getCount() == 1){
            setupDrawerAdapter();
        }

        if (pagerAdapter.getCount() > selectedTabId && viewPager.getCurrentItem() != selectedTabId)
            viewPager.setCurrentItem(selectedTabId);
    }

    public void addDrawerItem(CategoryModel categoryModel){
        drawerAdapter.addItem(categoryModel);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (drawerLayout.isDrawerOpen(drawerListView))
                drawerLayout.closeDrawer(drawerListView);

            pagerAdapter.performDrawerClick(
                    ((CategoryModel) drawerListView.getAdapter().getItem(position)),
                    tabLayout.getSelectedTabPosition()
            );
        }
    }
}
