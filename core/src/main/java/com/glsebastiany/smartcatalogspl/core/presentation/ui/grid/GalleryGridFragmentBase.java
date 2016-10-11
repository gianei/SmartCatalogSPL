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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.grid;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.nucleous.MvpRxFragmentBase;
import com.glsebastiany.smartcatalogspl.core.nucleous.Presenter;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.widget.SpacesItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(resName="fragment_gallery_visualization_grid")
public abstract class GalleryGridFragmentBase<P extends Presenter> extends MvpRxFragmentBase<P> implements GalleryGridCallbacks {

    public static final int MAX_ITEMS_TO_SHOW_SCROLL = 100;

    @Inject
    protected BaseAppDisplayFactory appDisplayFactory;

    @ViewById(resName="my_recycler_view")
    public RecyclerView recyclerView;

    @ViewById(resName="progressBar")
    public ProgressBar progressBar;

    @FragmentArg
    @InstanceState
    public String categoryId;

    protected GalleryGridItemsAdapterBase adapter;
    protected GridLayoutManager gridLayoutManager;

    @AfterViews
    protected void afterViews(){
        adapter = getGalleryGridItemsAdapter();
        recyclerView.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(getContext(), getStartingSpanSize());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(
                new SpacesItemDecoration(getContext().getResources().getDimensionPixelSize(R.dimen.grid_cards_spacing)));

        presenterAfterViews();
    }

    @NonNull
    protected abstract GalleryGridItemsAdapterBase getGalleryGridItemsAdapter();

    public void addItem(ItemModel itemModel){
        adapter.addItem(itemModel);
    }

    public void stopLoading(){
        if (progressBar!= null) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void injectApplicationComponent() {
        injectMe(this);
    }

    public abstract void injectMe(GalleryGridFragmentBase<P> galleryGridFragmentBase);

    protected abstract int getStartingSpanSize();

    public void moveToSubCategorySection(CategoryModel categoryModel) {
        int newPosition = ((GalleryGridItemsAdapterBase)recyclerView.getAdapter()).findCategoryPositionInItems(categoryModel);
        int visiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        int difference = visiblePosition - newPosition;
        if (Math.abs(difference) > MAX_ITEMS_TO_SHOW_SCROLL){
            recyclerView.scrollToPosition(newPosition + (MAX_ITEMS_TO_SHOW_SCROLL * (int) Math.signum(difference)));
            recyclerView.smoothScrollToPosition(newPosition);
        } else
            recyclerView.smoothScrollToPosition(newPosition);
    }

    public void switchToItemView( int position) {
        appDisplayFactory.switchToItemView(getActivity(), adapter.toStringArray(), position);

    }

}
