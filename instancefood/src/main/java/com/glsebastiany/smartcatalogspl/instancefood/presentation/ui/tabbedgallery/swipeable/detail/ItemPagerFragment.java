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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery.swipeable.detail;

import android.support.v4.app.Fragment;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.itempager.ItemPagerActivityBase;
import com.glsebastiany.smartcatalogspl.instancefood.R;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.ItemsGroupComponent;
import com.glsebastiany.smartcatalogspl.core.presentation.di.HasComponent;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.activity_gallery_visualization_pager)
public class ItemPagerFragment extends ItemPagerActivityBase implements HasComponent<ItemsGroupComponent> {

    public static ItemPagerActivityBase newInstance(int position) {
        return ItemPagerFragment_.builder().itemPosition(position).build();
    }

    public ItemsGroupComponent getComponent() {
        return ((HasComponent<ItemsGroupComponent>) getParentFragment()).getComponent();
    }

    @Override
    protected void injectMe(ItemPagerActivityBase itemPagerActivityBase) {
        getComponent().inject(itemPagerActivityBase);
    }

    @Override
    protected Fragment getItem(int position) {
        return ItemDetailFragment.newInstance(position);
    }

    @Override
    protected void setupComponent() {

    }


}
