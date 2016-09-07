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

package com.glsebastiany.smartcatalogspl.di.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.ui.tabbedgallery.ActivityGallery_;
import com.glsebastiany.smartcatalogspl.ui.tabbedgallery.swipeable.FragmentGalleryVisualization;
import com.glsebastiany.smartcatalogspl.ui.tabbedgallery.swipeable.detail.FragmentItemPager;

import javax.inject.Inject;

public class AppDisplayFactory implements BaseAppDisplayFactory {

    @Inject
    Context context;

    @Inject
    public AppDisplayFactory(){};

    @Override
    public Fragment provideGalleryFragment(String category){
        return FragmentGalleryVisualization.newInstance(category);
    }

    @Override
    public void startGalleryActivity() {
        ActivityGallery_.intent(context).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
    }

    @Override
    public void switchToItemView(FragmentManager fragmentManager, int position) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentItemPager fragmentItemPager = FragmentItemPager.newInstance(position);
        fragmentTransaction.add(R.id.gallery_visualization, fragmentItemPager);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
