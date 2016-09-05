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

package com.glsebastiany.smartcatalogspl.presentationfood.foods;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.glsebastiany.smartcatalogspl.presentation.DisplayFactory;
import com.glsebastiany.smartcatalogspl.presentation.ItemViewHolder;
import com.glsebastiany.smartcatalogspl.presentationfood.GalleryGridItemsAdapter;

import javax.inject.Inject;

public class FoodDisplayFactory implements DisplayFactory {

    @Inject
    FoodDrawerAdapter foodDrawerAdapter;

    @Inject
    public FoodDisplayFactory(){};

    @Override
    public ItemViewHolder drawerViewHolder(ViewGroup parent) {
        return FoodViewHolder.createInstance(parent);
    }

    @Override
    public BaseAdapter drawerAdapter() {
        return foodDrawerAdapter;
    }


}