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

package com.glsebastiany.smartcatalogspl.ui.tabbedgallery.swipeable.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.di.components.ItemsGroupComponent;
import com.glsebastiany.smartcatalogspl.di.helper.HasComponent;

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

@EFragment(R.layout.fragment_gallery_visualization_detail_pager)
public class FragmentItemPager extends BaseFragment implements HasComponent<ItemsGroupComponent> {

    @FragmentArg
    int itemPosition;

    @ViewById(R.id.pager)
    ViewPager mPager;

    @Inject
    Observable<ItemModel> itemModelObservable;

    private Subscription subscription;

    public static FragmentItemPager newInstance(int position) {
        return FragmentItemPager_.builder().itemPosition(position).build();
    }

    @AfterViews
    public void afterViews() {

        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPager.setCurrentItem(itemPosition);

    }


    @Override
    protected void injectComponent() {
        getComponent().inject(this);
    }

    @Override
    public ItemsGroupComponent getComponent() {
        return ((HasComponent<ItemsGroupComponent>) getParentFragment()).getComponent();
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
            return FragmentItemDetail.newInstance(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


}
