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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.grid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.MenuItem;

import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.RequiresPresenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridItemsAdapterBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridCallbacks;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.preferences.SharedPreferencesZoom_;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ApplicationComponent;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EFragment(R.layout.fragment_gallery_visualization_grid)
@RequiresPresenter(GalleryGridPresenter.class)
public class GalleryGridFragment extends GalleryGridFragmentBase<GalleryGridPresenter> {

    @Pref
    SharedPreferencesZoom_ preferencesZoom;

    public static GalleryGridCallbacks newInstance(String searchQuery, boolean isCategoryIdQuery) {
        return GalleryGridFragment_.builder()
                .searchQuery(searchQuery)
                .isCategoryIdQuery(isCategoryIdQuery)
                .build();
    }

    @Override
    public void injectMe(GalleryGridFragmentBase<GalleryGridPresenter> galleryGridFragmentBase) {
        AndroidApplication.<ApplicationComponent>singleton().getApplicationComponent().inject(this);
    }

    @Override
    @NonNull
    protected GalleryGridItemsAdapterBase getGalleryGridItemsAdapter() {
        return new GalleryGridItemsAdapter(this);
    }

    @Override
    protected int getStartingSpanSize() {
        return preferencesZoom.gridZoom().get();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId_ = item.getItemId();
        if (itemId_ == R.id.menu_zoom_in) {
            zoomIn();
        }
        if (itemId_ == R.id.menu_zoom_out) {
            zoomOut();
        }

        return super.onOptionsItemSelected(item);
    }




    protected void zoomIn() {
        int newSpanSize = preferencesZoom.gridZoom().get();
        if (!isGridSpanAtMinimal()) {
            newSpanSize--;
            if (((GridLayoutManager)recyclerView.getLayoutManager()).getSpanCount() - 1 == newSpanSize){
                preferencesZoom.gridZoom().put(newSpanSize);
                gridLayoutManager.setSpanCount(newSpanSize);
                gridLayoutManager.requestLayout();
            }
        }
    }

    protected void zoomOut() {
        int newSpanSize = preferencesZoom.gridZoom().get();
        if (!isGridSpanAtMaximum()) {
            newSpanSize++;
            if (((GridLayoutManager)recyclerView.getLayoutManager()).getSpanCount() + 1 == newSpanSize){
                preferencesZoom.gridZoom().put(newSpanSize);
                gridLayoutManager.setSpanCount(newSpanSize);
                gridLayoutManager.requestLayout();
            }
        }
    }

    private boolean isGridSpanAtMinimal() {
        return preferencesZoom.gridZoom().get() <= getActivity().getResources().getInteger(R.integer.grid_span_min);
    }

    private boolean isGridSpanAtMaximum() {
        return preferencesZoom.gridZoom().get() >= getActivity().getResources().getInteger(R.integer.grid_span_max);
    }

}
