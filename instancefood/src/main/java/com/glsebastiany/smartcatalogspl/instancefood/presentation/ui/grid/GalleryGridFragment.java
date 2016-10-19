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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.grid;

import android.support.annotation.NonNull;

import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.RequiresPresenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridCallbacks;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridItemsAdapterBase;
import com.glsebastiany.smartcatalogspl.instancefood.R;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridFragmentBase;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.ApplicationComponent;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_gallery_visualization_grid)
@RequiresPresenter(GalleryGridPresenter.class)
public class GalleryGridFragment extends GalleryGridFragmentBase<GalleryGridPresenter> {

    public static GalleryGridCallbacks newInstance(String searchQuery, boolean isCategoryIdQuery) {
        return GalleryGridFragment_.builder()
                .searchQuery(searchQuery)
                .isCategoryIdQuery(isCategoryIdQuery)
                .build();
    }

    @NonNull
    @Override
    protected GalleryGridItemsAdapterBase getGalleryGridItemsAdapter() {
        return new GalleryGridItemsAdapter(this);
    }

    @Override
    public void injectMe(GalleryGridFragmentBase<GalleryGridPresenter> activityGalleryBase) {
        AndroidApplication.<ApplicationComponent>singleton().getApplicationComponent().inject(activityGalleryBase);
    }

    @Override
    protected int getStartingSpanSize() {
        return 3;
    }


}
