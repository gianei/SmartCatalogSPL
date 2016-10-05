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
public abstract class GalleryGridFragmentBase<P extends Presenter> extends MvpRxFragmentBase<P> {

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
    protected void setupComponent() {
        injectMe(this);
    }

    @Override
    protected void injectComponent() {

    }

    protected abstract int getStartingSpanSize();

    public abstract void injectMe(GalleryGridFragmentBase<P> galleryGridFragmentBase);

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