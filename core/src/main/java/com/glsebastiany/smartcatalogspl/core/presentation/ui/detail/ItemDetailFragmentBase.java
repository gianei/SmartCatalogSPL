package com.glsebastiany.smartcatalogspl.core.presentation.ui.detail;

import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.nucleous.MvpRxFragmentBase;
import com.glsebastiany.smartcatalogspl.core.nucleous.Presenter;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

@EFragment(resName="fragment_gallery_visualization_detail_item_stub")
public abstract class ItemDetailFragmentBase<P extends Presenter> extends MvpRxFragmentBase<P> {

    @ViewById(resName="item_detail_stub")
    public ViewStub itemDetailStub;

    @ViewById(resName="progressBar")
    public ProgressBar progressBar;

    @FragmentArg
    @InstanceState
    public String itemId;

    @Override
    protected void setupComponent() {

    }

    @Override
    protected void injectComponent() {

    }

    public void addItem(ItemModel itemModel){

        inflateViewStub(itemModel);

        progressBar.setVisibility(View.GONE);
        itemDetailStub.setVisibility(View.VISIBLE);
    }

    protected abstract void inflateViewStub(ItemModel itemModel);
}
