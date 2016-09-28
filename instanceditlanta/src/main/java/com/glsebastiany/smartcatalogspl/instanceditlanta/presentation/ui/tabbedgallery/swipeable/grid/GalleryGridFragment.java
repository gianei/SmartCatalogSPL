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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.tabbedgallery.swipeable.grid;

import android.support.v7.widget.LinearLayoutManager;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.presentation.di.HasComponent;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.swipeable.grid.GalleryGridFragmentBase;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ItemsGroupComponent;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_gallery_visualization_grid)
public class GalleryGridFragment extends GalleryGridFragmentBase implements HasComponent<ItemsGroupComponent> {

    public static final int MAX_ITEMS_TO_SHOW_SCROLL = 100;

    @Override
    public ItemsGroupComponent getComponent() {
        return ((HasComponent<ItemsGroupComponent>) getParentFragment()).getComponent();
    }

    @Override
    protected void injectMe(GalleryGridFragmentBase activityGalleryBase) {
        getComponent().inject(activityGalleryBase);
    }

    @Override
    protected void setupComponent() {

    }

    public void moveToSubCategorySection(CategoryModel categoryModel) {
        int newPosition = ((GalleryGridItemsAdapter)recyclerView.getAdapter()).findCategoryPositionInItems(categoryModel);
        int visiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        int difference = visiblePosition - newPosition;
        if (Math.abs(difference) > MAX_ITEMS_TO_SHOW_SCROLL){
            recyclerView.scrollToPosition(newPosition + (MAX_ITEMS_TO_SHOW_SCROLL * (int) Math.signum(difference)));
            recyclerView.smoothScrollToPosition(newPosition);
        } else
            recyclerView.smoothScrollToPosition(newPosition);
    }
}
