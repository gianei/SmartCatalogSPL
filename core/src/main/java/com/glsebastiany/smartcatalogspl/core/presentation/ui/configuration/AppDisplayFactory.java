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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.ItemDetailFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itemsets.ItemSetsCallbacks;

import javax.inject.Inject;

public class AppDisplayFactory implements BaseAppDisplayFactory {

    @Inject
    HomeScreenConfigurator homeScreenConfigurator;

    @Inject
    ItemDetailConfigurator itemDetailConfigurator;

    @Inject
    ItemSetsConfigurator itemSetsConfigurator;

    @Inject
    ToolbarConfigurator toolbarConfigurator;

    @Inject
    MenuConfigurator menuConfigurator;

    @Inject
    ImagesHelper imagesHelper;

    @Inject
    public AppDisplayFactory() {
    }

    @Override
    public void startHomeScreen(AppCompatActivity activityBase) {
        homeScreenConfigurator.startHomeScreen(activityBase);
    }

    @Override
    public ItemSetsCallbacks provideItemSetFragment(String searchQuery, boolean isCategoryIdQuery){
        return itemSetsConfigurator.provideItemSetFragment(searchQuery, isCategoryIdQuery);
    }

    @Override
    public void switchToItemView(FragmentActivity fromActivity, String[] categoriesIds, int position) {
        itemDetailConfigurator.switchToItemView(fromActivity, categoriesIds, position);
    }

    @Override
    public ItemDetailFragmentBase getItemDetailFragment(String itemId) {
        return itemDetailConfigurator.getItemDetailFragment(itemId);
    }

    @Override
    public void configureToolbarLogo(AppCompatActivity activity, Toolbar toolbar) {
        toolbarConfigurator.configureToolbarLogo(activity, toolbar);
    }

    @Override
    public boolean inflateMenus(AppCompatActivity activity, Menu menu) {
        return menuConfigurator.inflateMenus(activity, menu);
    }

    @Override
    public boolean menuSelected(AppCompatActivity activity, MenuItem menuItem) {
        return menuConfigurator.menuSelected(activity, menuItem);
    }

    @Override
    public void loadDetailImageWithGlide(Context context, ItemBasicModel baseItem, ImageView intoView) {
        imagesHelper.loadDetailImageWithGlide(context, baseItem, intoView);
    }

    @Override
    public void loadCardImageWithGlide(Context context, ItemBasicModel baseItem, ImageView intoView) {
        imagesHelper.loadCardImageWithGlide(context, baseItem, intoView);
    }

}
