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

import android.view.ViewStub;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.di.components.ItemsGroupComponent;
import com.glsebastiany.smartcatalogspl.di.helper.HasComponent;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseSwipeableController;

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

@EFragment(R.layout.fragment_gallery_visualization_detail_item_stub)
public class FragmentItemDetail extends BaseFragment {

    @Inject
    Observable<ItemModel> itemModelObservable;

    @Inject
    BaseSwipeableController galleryGridController;

    @FragmentArg
    int position;

    @ViewById(R.id.item_detail_stub)
    ViewStub itemDetailStub;

    private Subscription subscription;

    public static FragmentItemDetail newInstance(int position){
        return FragmentItemDetail_.builder().position(position).build();
    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


    @Override
    protected void injectComponent() {
        ((HasComponent<ItemsGroupComponent>) getParentFragment()).getComponent().inject(this);
    }
}
