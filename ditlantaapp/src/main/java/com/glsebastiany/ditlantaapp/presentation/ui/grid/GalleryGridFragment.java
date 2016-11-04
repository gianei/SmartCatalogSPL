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

package com.glsebastiany.ditlantaapp.presentation.ui.grid;

import com.glsebastiany.smartcatalogspl.core.data.item.ItemExtendedModel;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.extended.GalleryGridPresenterExtended;


public abstract class GalleryGridFragment extends GalleryGridFragmentBase<GalleryGridPresenterExtended, ItemExtendedModel> {
/*
    @Pref
    SharedPreferencesZoom_ preferencesZoom;

    public static GalleryGridCallbacks newInstance(String searchQuery, boolean isCategoryIdQuery) {
        return GalleryGridFragment_.builder()
                .searchQuery(searchQuery)
                .isCategoryIdQuery(isCategoryIdQuery)
                .build();
    }

    @Override
    public void injectMe(GalleryGridFragmentBase<GalleryGridPresenterExtended> galleryGridFragmentBase) {
        //AndroidApplication.<ApplicationComponent>singleton().getApplicationComponent().inject(galleryGridFragmentBase);
    }

    @Override
    @NonNull
    protected GalleryGridItemsAdapterBase getGalleryGridItemsAdapter() {
        return new GalleryGridItemsAdapterExtended(this);
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
            return false;
        }
        if (itemId_ == R.id.menu_zoom_out) {
            zoomOut();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void zoomIn() {
        int actualSpanSize = preferencesZoom.gridZoom().get();
        if (((GridLayoutManager)recyclerView.getLayoutManager()).getSpanCount() == actualSpanSize){
            if (!isGridSpanAtMinimal()) {
                actualSpanSize--;
                preferencesZoom.gridZoom().put(actualSpanSize);
                changeLayoutSpan(actualSpanSize);
            }
        } else {
            changeLayoutSpan(actualSpanSize);
        }
    }

    protected void zoomOut() {
        int actualSpanSize = preferencesZoom.gridZoom().get();
        if (((GridLayoutManager)recyclerView.getLayoutManager()).getSpanCount() == actualSpanSize){
            if (!isGridSpanAtMaximum()) {
                actualSpanSize++;
                preferencesZoom.gridZoom().put(actualSpanSize);
                changeLayoutSpan(actualSpanSize);
            }
        } else {
            changeLayoutSpan(actualSpanSize);
        }
    }

    private void changeLayoutSpan(int actualSpanSize) {
        gridLayoutManager.setSpanCount(actualSpanSize);
        gridLayoutManager.requestLayout();
    }

    private boolean isGridSpanAtMinimal() {
        return preferencesZoom.gridZoom().get() <= getActivity().getResources().getInteger(R.integer.grid_span_min);
    }

    private boolean isGridSpanAtMaximum() {
        return preferencesZoom.gridZoom().get() >= getActivity().getResources().getInteger(R.integer.grid_span_max);
    }
    */

}
