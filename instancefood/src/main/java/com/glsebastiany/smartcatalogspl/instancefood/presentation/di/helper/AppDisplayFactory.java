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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.di.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.ItemDetailFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridCallbacks;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridFragmentBase;
import com.glsebastiany.smartcatalogspl.instancefood.R;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itempager.ItemPagerActivityBase;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.detail.ItemDetailFragment;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.grid.GalleryGridFragment;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.itempager.ItemPagerActivity_;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.main.MainActivity_;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery.TabbedGalleryActivity;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery.TabbedGalleryFragment;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.itempager.ItemPagerActivity;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.grid.GalleryGridFragment_;

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
    public GalleryGridCallbacks provideGalleryGridFragment(String category){
        return GalleryGridFragment.newInstance(category);
    }

    @Override
    public GalleryGridFragmentBase provideGalleryGridFragment() {
        return GalleryGridFragment_.builder().build();
    }

    @Override
    public void startMainActivity(AppCompatActivity activityBase) {
        MainActivity_.intent(activityBase).start();
        activityBase.finish();
    }

    @Override
    public void startLoginActivity() {
        throw new RuntimeException("NOT IMPLEMENTED!");
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
        return ItemDetailFragment.newInstance(itemId);
    }



}
