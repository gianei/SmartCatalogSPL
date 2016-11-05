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

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.ItemDetailFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itemsets.ItemSetsCallbacks;


public interface BaseAppDisplayFactory {

    interface HomeScreenConfigurator{
        void startHomeScreen(AppCompatActivity activityBase);
    }

    interface ItemSetsConfigurator{
        ItemSetsCallbacks provideItemSetFragment(String searchQuery, boolean isCategoryIdQuery);
    }

    interface ItemDetailConfigurator{
        void switchToItemView(FragmentActivity fromActivity, String[] categoriesIds, int position);
        ItemDetailFragmentBase getItemDetailFragment(String itemId);
    }

    void startHomeScreen(AppCompatActivity activityBase);


    ItemSetsCallbacks provideItemSetFragment(String searchQuery, boolean isCategoryIdQuery);


    void switchToItemView(FragmentActivity fromActivity, String[] categoriesIds, int position);


    ItemDetailFragmentBase getItemDetailFragment(String itemId);
}
