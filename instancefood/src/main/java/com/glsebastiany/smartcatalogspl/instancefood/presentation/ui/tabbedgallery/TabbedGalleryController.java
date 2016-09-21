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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseTabbedGalleryController;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

public class TabbedGalleryController extends BaseTabbedGalleryController {

    @Inject
    CategoryUseCases categoryUseCases;

    @Inject
    FragmentManager fragmentManager;

    @Inject
    BaseAppDisplayFactory baseAppDisplayFactory;

    @Inject
    public TabbedGalleryController(){}


    public void setupPager(Context context, final ProgressBar progressBar, final ViewPager viewPager, List<String> categoriesIds){

        Observable<CategoryModel> observable = categoryUseCases.findCategory(categoriesIds);

        viewPager.setAdapter(new PagerAdapter(fragmentManager, observable, baseAppDisplayFactory));

        endSubscriptions();

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
                progressBar.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
            }
        }));

    }

    public void setupSlidingTabs(TabLayout tabLayout, ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void setupDrawerAdapter(Context context, ListView listView) {
        listView.setAdapter(new DrawerAdapter(context, categoryUseCases.drawerCategories()));
    }
}
