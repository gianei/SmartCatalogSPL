package com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.swipeable.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
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

@EFragment(resName="fragment_gallery_visualization_detail_pager")
public abstract class ItemPagerFragmentBase extends FragmentBase {

    @Inject
    Observable<ItemModel> itemModelObservable;
    private Subscription subscription;

    @FragmentArg
    public int itemPosition;

    @ViewById(resName="pager")
    public ViewPager mPager;

    @AfterViews
    public void afterViews() {

        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPager.setCurrentItem(itemPosition);

    }


    @Override
    protected void injectComponent() {
        injectMe(this);
    }

    protected abstract void injectMe(ItemPagerFragmentBase activityGalleryBase);


    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private List<ItemModel> items = new LinkedList<>();

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);

            subscription = itemModelObservable.subscribe(new Observer<ItemModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ItemModel itemModel) {
                    items.add(itemModel);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public Fragment getItem(int position) {
            return ItemPagerFragmentBase.this.getItem(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }

    protected abstract Fragment getItem(int position);
}
