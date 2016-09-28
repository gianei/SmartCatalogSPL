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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery.swipeable;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseSwipeableGalleryController;
import com.glsebastiany.smartcatalogspl.core.presentation.widget.SpacesItemDecoration;
import com.glsebastiany.smartcatalogspl.instancefood.R;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery.swipeable.grid.GalleryGridItemsAdapter;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

public class SwipeableGalleryController extends BaseSwipeableGalleryController {

    @Inject
    ItemUseCases itemUseCases;

    @Inject
    BaseAppDisplayFactory baseAppDisplayFactory;

    @Inject
    public SwipeableGalleryController(){}

    public Observable<ItemModel> getItemsObservableInternal(String categoryId){
        return itemUseCases.allFromCategory(categoryId);
    }

    public void setupRecyclerView(Context context, Observable<ItemModel> observable, final ProgressBar progressBar, final RecyclerView recyclerView, FragmentManager fragmentManager){

        //TODO check for leaking
        //endSubscriptions();
        addSubscription(observable.subscribe(new Observer<ItemModel>() {
            @Override
            public void onCompleted() {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ItemModel itemModel) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }));

        recyclerView.setAdapter(new GalleryGridItemsAdapter(observable, fragmentManager, baseAppDisplayFactory));
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        recyclerView.addItemDecoration(
                new SpacesItemDecoration(context.getResources().getDimensionPixelSize(R.dimen.grid_cards_spacing)));

    }

    @Override
    public void inflateItemDetailStub(ViewStub viewStub, ItemModel itemModel){
        viewStub.setLayoutResource(R.layout.fragment_gallery_visualization_detail_item);
        View newView = viewStub.inflate();
        TextView textView = (TextView) newView.findViewById(R.id.textViewDetailDescription);
        textView.setText(itemModel.getStringId());
    }
}
