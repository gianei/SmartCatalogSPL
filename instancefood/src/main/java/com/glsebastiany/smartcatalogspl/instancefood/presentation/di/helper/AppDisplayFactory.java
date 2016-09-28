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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.glsebastiany.smartcatalogspl.instancefood.R;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.swipeable.detail.ItemPagerFragmentBase;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.main.MainActivity_;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery.TabbedGalleryActivity;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery.TabbedGalleryFragment;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery.swipeable.SwipeableGalleryFragment;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery.swipeable.detail.ItemPagerFragment;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery.swipeable.grid.GalleryGridFragment_;

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
    public Fragment provideGalleryVisualizationFragment(String category){
        return SwipeableGalleryFragment.newInstance(category);
    }

    @Override
    public Fragment provideGalleryGridFragment() {
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
    public void startGalleryActivity(List<String> categoriesId) {
        TabbedGalleryActivity.start(context, categoriesId);
    }

    @Override
    public void switchToItemView(FragmentManager fragmentManager, int position) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ItemPagerFragmentBase fragmentItemPager = ItemPagerFragment.newInstance(position);
        fragmentTransaction.add(R.id.gallery_visualization, fragmentItemPager);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
