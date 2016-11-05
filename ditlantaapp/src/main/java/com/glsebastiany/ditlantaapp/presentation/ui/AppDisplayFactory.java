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

package com.glsebastiany.ditlantaapp.presentation.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.glsebastiany.ditlantaapp.presentation.ui.login.LoginActivity_;
import com.glsebastiany.ditlantaapp.presentation.ui.main.MainActivity_;

import com.glsebastiany.ditlantaapp.presentation.ui.tabbedgallery.TabbedGalleryActivity;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.ItemDetailFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.extended.ItemDetailFragmentExtended;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itemsets.grid.ItemSetsCallbacks;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itemsets.grid.extended.GalleryGridFragmentExtended;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itempager.ItemPagerActivity_;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * This class makes actions between screens. It cane be replaced for other needs for different apps,
 * but all must be placed here.
 */
public class AppDisplayFactory implements BaseAppDisplayFactory {

    @Inject
    Context context;

    @Inject
    public AppDisplayFactory(){};

    @Override
    public Fragment provideGalleryFragment(String[] categoriesIds) {
        return TabbedGalleryFragment.newInstance(categoriesIds);
    }

    @Override
    public ItemSetsCallbacks provideItemSetFragment(String searchQuery, boolean isCategoryIdQuery){
        return GalleryGridFragmentExtended.newInstance(searchQuery, isCategoryIdQuery);
    }

    @Override
    public void startHomeScreen(AppCompatActivity activityBase) {
        MainActivity_.intent(activityBase).start();
        activityBase.finish();
    }

    @Override
    public void startLoginActivity() {
        LoginActivity_
                .intent(context)
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION)
                .start();
    }

    @Override
    public void startGalleryActivity(Context fromContext, List<String> categoriesId) {
        TabbedGalleryActivity.start(fromContext, categoriesId);
    }

    @Override
    public void switchToItemView(FragmentActivity fromActivity, String[] categoriesIds, int position) {
        ItemPagerActivity_
                .intent(fromActivity)
                .categoriesIds(categoriesIds)
                .itemPosition(position)
                .start();
    }

    @Override
    public ItemDetailFragmentBase getItemDetailFragment(String itemId) {
        return ItemDetailFragmentExtended.newInstance(itemId);
    }


}
