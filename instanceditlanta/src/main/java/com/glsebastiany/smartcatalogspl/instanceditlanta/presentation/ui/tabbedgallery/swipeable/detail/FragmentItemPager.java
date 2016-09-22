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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.tabbedgallery.swipeable.detail;

import android.support.v4.app.Fragment;

import com.glsebastiany.smartcatalogspl.core.presentation.di.HasComponent;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.swipeable.detail.FragmentItemPagerBase;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ItemsGroupComponent;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_gallery_visualization_detail_pager)
public class FragmentItemPager extends FragmentItemPagerBase implements HasComponent<ItemsGroupComponent> {

    public static FragmentItemPagerBase newInstance(int position) {
        return FragmentItemPager_.builder().itemPosition(position).build();
    }

    public ItemsGroupComponent getComponent() {
        return ((HasComponent<ItemsGroupComponent>) getParentFragment()).getComponent();
    }

    @Override
    protected void injectMe(FragmentItemPagerBase fragmentItemPagerBase) {
        getComponent().inject(fragmentItemPagerBase);
    }

    @Override
    protected Fragment getItem(int position) {
        return FragmentItemDetail.newInstance(position);
    }

    @Override
    protected void setupComponent() {

    }


}
