package com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.swipeable.detail;

import android.view.ViewStub;

import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseSwipeableController;
import com.glsebastiany.smartcatalogspl.core.presentation.system.FragmentBase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

@EFragment(resName="fragment_gallery_visualization_detail_item_stub")
public abstract class FragmentItemDetailBase extends FragmentBase {

    @Inject
    Observable<ItemModel> itemModelObservable;

    @Inject
    BaseSwipeableController galleryGridController;

    @FragmentArg
    public int position;

    @ViewById(resName="item_detail_stub")
    public ViewStub itemDetailStub;

    private Subscription subscription;

    @AfterViews
    public void afterViews() {

        subscription = itemModelObservable.subscribe(new Observer<ItemModel>() {

            private List<ItemModel> items = new LinkedList<>();

            @Override
            public void onCompleted() {
                galleryGridController.inflateItemDetailStub(itemDetailStub, items.get(position));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ItemModel itemModel) {
                items.add(itemModel);
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    protected void injectComponent() {
        injectMe(this);
    }

    protected abstract void injectMe(FragmentItemDetailBase fragmentItemDetailBase);
}
