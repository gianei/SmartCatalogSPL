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

package com.glsebastiany.smartcatalogspl.ui.tabbedgallery.swipeable.detail;

import com.glsebastiany.smartcatalogspl.instancefood.R;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.swipeable.detail.FragmentItemDetailBase;
import com.glsebastiany.smartcatalogspl.di.components.ItemsGroupComponent;
import com.glsebastiany.smartcatalogspl.di.helper.HasComponent;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_gallery_visualization_detail_item_stub)
public class FragmentItemDetail extends FragmentItemDetailBase {

    public static FragmentItemDetailBase newInstance(int position){
        return FragmentItemDetail_.builder().position(position).build();
    }

    @Override
    protected void setupComponent() {

    }

    @Override
    protected void injectMe(FragmentItemDetailBase fragmentItemDetailBase) {
        ((HasComponent<ItemsGroupComponent>) getParentFragment()).getComponent().inject(fragmentItemDetailBase);

    }

}
