package com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.swipeable.grid;

import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseGalleryGridController;
import com.glsebastiany.smartcatalogspl.core.presentation.system.FragmentBase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import rx.Observable;

@EFragment(resName="fragment_gallery_visualization_grid")
public abstract class GalleryGridFragmentBase extends FragmentBase {

    @Inject
    protected
    BaseGalleryGridController galleryGridController;

    @Inject
    Observable<ItemModel> itemModelObservable;

    @ViewById(resName="my_recycler_view")
    public RecyclerView recyclerView;

    @ViewById(resName="progressBar")
    public ProgressBar progressBar;

    @Override
    protected void injectComponent() {
        injectMe(this);
    }

    protected abstract void injectMe(GalleryGridFragmentBase activityGalleryBase);

    protected abstract int getStartingSpanSize();

    @AfterViews
    public void afterViews() {
        galleryGridController.bindAndSetup(
                getActivity(),
                progressBar,
                recyclerView,
                getStartingSpanSize(),
                getFragmentManager(),
                itemModelObservable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        galleryGridController.endSubscriptions();
    }

}
