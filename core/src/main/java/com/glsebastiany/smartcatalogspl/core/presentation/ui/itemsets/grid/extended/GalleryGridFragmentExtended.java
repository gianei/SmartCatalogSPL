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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.itemsets.grid.extended;

import android.support.annotation.NonNull;

import com.glsebastiany.smartcatalogspl.core.data.item.ItemExtendedModel;
import com.glsebastiany.smartcatalogspl.core.presentation.mvpFramework.RequiresPresenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itemsets.ItemSetsCallbacks;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itemsets.grid.GalleryGridFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itemsets.grid.GalleryGridItemsAdapterBase;

import org.androidannotations.annotations.EFragment;

@EFragment(resName="fragment_gallery_visualization_grid")
@RequiresPresenter(GalleryGridPresenterExtended.class)
public class GalleryGridFragmentExtended extends GalleryGridFragmentBase<GalleryGridPresenterExtended, ItemExtendedModel> {

    public static ItemSetsCallbacks newInstance(String searchQuery, boolean isCategoryIdQuery) {
        return GalleryGridFragmentExtended_.builder()
                .searchQuery(searchQuery)
                .isCategoryIdQuery(isCategoryIdQuery)
                .build();
    }

    @NonNull
    @Override
    protected GalleryGridItemsAdapterBase<ItemExtendedModel> getGalleryGridItemsAdapter() {
        return new GalleryGridItemsAdapterExtended(this);
    }
}
