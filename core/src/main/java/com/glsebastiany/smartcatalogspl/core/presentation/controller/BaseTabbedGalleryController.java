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

    }

    private void setupPager(){





    }







}
