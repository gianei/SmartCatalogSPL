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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.itemdetail;

import android.support.v4.app.FragmentActivity;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.AppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.ItemDetailFragment;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.ItemDetailFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.extended.ItemDetailFragmentExtended;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itempager.ItemPagerActivity_;

import javax.inject.Inject;

public class SwipeListExtendedDetail implements AppDisplayFactory.ItemDetailConfigurator {

    @Inject
    public SwipeListExtendedDetail() {
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
