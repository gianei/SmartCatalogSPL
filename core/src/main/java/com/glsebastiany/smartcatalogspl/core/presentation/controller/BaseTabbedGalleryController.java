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

package com.glsebastiany.smartcatalogspl.core.presentation.controller;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;

import java.util.List;

import rx.Observable;
import rx.Observer;

public abstract class BaseTabbedGalleryController extends BaseSubscriptionedController{
    protected Context context;
    private ProgressBar progressBar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private List<String> categoriesIds;

    private Observable<CategoryModel> observable;

    protected abstract FragmentStatePagerAdapter getFragmentStatePagerAdapter(Observable<CategoryModel> observable);
    protected abstract BaseAdapter getDrawerAdapter(String categoryId);
    protected abstract Observable<CategoryModel> getCategoryObservable(List<String> categoriesIds);
    protected abstract DrawerClickSupport getDrawerClickSupport();

    public void bindAndSetup(
            Context context,
            ProgressBar progressBar,
            ViewPager viewPager,
            TabLayout tabLayout,
            DrawerLayout drawerLayout,
            ListView drawerListView,
            List<String> categoriesIds
    ){
        this.context = context;
        this.progressBar = progressBar;
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        this.drawerLayout = drawerLayout;
        this.drawerListView = drawerListView;
        this.categoriesIds = categoriesIds;

        observable = ObservableHelper.setupThreads(getCategoryObservable(categoriesIds).cache());

        setupPager();
        setupSlidingTabs();
        setupDrawerClick();
        setupDrawerAdapter(0);

    }

    private void setupPager(){

        viewPager.setAdapter(getFragmentStatePagerAdapter(observable));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                setupDrawerAdapter(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        addSubscription(observable.subscribe(new Observer<CategoryModel>() {
            @Override
            public void onCompleted() {
                progressBar.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CategoryModel categoryModel) {

            }
        }));

    }

    private void setupSlidingTabs(){
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    private void setupDrawerAdapter(int position) {

        drawerListView.setAdapter(getDrawerAdapter(categoriesIds.get(position)));

    }

    private void setupDrawerClick() {
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (drawerLayout.isDrawerOpen(drawerListView))
                drawerLayout.closeDrawer(drawerListView);

            if (getDrawerClickSupport() != null){
                getDrawerClickSupport().performDrawerClick(
                        ((CategoryModel) drawerListView.getAdapter().getItem(position)),
                        tabLayout.getSelectedTabPosition()
                );
            }

        }
    }

    public interface DrawerClickSupport{
        void performDrawerClick(CategoryModel categoryModel, int currentTabPosition);
    }
}
