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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.itemsets.grid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemId;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.MvpRxFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.Presenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itemsets.ItemSetsCallbacks;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.splash.SplashScreenBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.MyGridLayoutManager;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.SpacesItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

@EFragment(resName="fragment_gallery_visualization_grid")
public abstract class GalleryGridFragmentBase<P extends Presenter, I extends ItemId> extends MvpRxFragmentBase<P> implements ItemSetsCallbacks {

    public static final int MAX_ITEMS_TO_SHOW_SCROLL = 100;

    protected BaseAppDisplayFactory appDisplayFactory;

    @ViewById(resName="my_recycler_view")
    public RecyclerView recyclerView;

    @ViewById(resName="progressBar")
    public ProgressBar progressBar;

    @FragmentArg
    @InstanceState
    public String searchQuery;

    @FragmentArg
    @InstanceState
    public boolean isCategoryIdQuery;

    protected GalleryGridItemsAdapterBase<I> adapter;
    protected RecyclerView.LayoutManager gridLayoutManager;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        appDisplayFactory = SplashScreenBase.getInstance().baseAppDisplayFactory;
    }

    @AfterViews
    protected void afterViews(){
        adapter = getGalleryGridItemsAdapter();
        recyclerView.setAdapter(adapter);
        gridLayoutManager = getMyGridLayoutManager();
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(
                new SpacesItemDecoration(getContext().getResources().getDimensionPixelSize(R.dimen.grid_cards_spacing)));

        presenterAfterViews();
    }

    @NonNull
    protected RecyclerView.LayoutManager getMyGridLayoutManager() {
        return new MyGridLayoutManager(getContext(), getStartingSpanSize());
    }

    @NonNull
    protected abstract GalleryGridItemsAdapterBase<I> getGalleryGridItemsAdapter();

    public void addItem(I itemComposition){
        adapter.addItem(itemComposition);
    }

    public void stopLoading(){
        if (progressBar!= null) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    protected int getStartingSpanSize(){
        return 2;
    }

    public void moveToSubCategorySection(CategoryModel categoryModel) {
        int newPosition = ((GalleryGridItemsAdapterBase)recyclerView.getAdapter()).findCategoryPositionInItems(categoryModel);
        if (newPosition < 0)
            return;

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
