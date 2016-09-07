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

package com.glsebastiany.smartcatalogspl.presentationfood.controller;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.data.CategoryRepository;
import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.presentation.controller.BaseGalleryGridController;
import com.glsebastiany.smartcatalogspl.presentation.widget.SpacesItemDecoration;
import com.glsebastiany.smartcatalogspl.presentationfood.GalleryGridItemsAdapter;
import com.glsebastiany.smartcatalogspl.presentationfood.R;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

public class GalleryGridController extends BaseGalleryGridController {

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    ItemUseCases itemUseCases;

    @Inject
    BaseAppDisplayFactory baseAppDisplayFactory;

    @Inject
    public GalleryGridController(){}

    public Observable<ItemModel> getItemsObservable(String categoryId){
        return itemUseCases.allFromCategory(categoryId);
    }


    public void setupRecyclerView(Context context, Observable<ItemModel> observable, final ProgressBar progressBar, final RecyclerView recyclerView, FragmentManager fragmentManager){
        observable.subscribe(new Observer<ItemModel>() {
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
        });

        recyclerView.setAdapter(new GalleryGridItemsAdapter(observable, fragmentManager, baseAppDisplayFactory));
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        recyclerView.addItemDecoration(
                new SpacesItemDecoration(context.getResources().getDimensionPixelSize(R.dimen.grid_cards_spacing)));

    }
}
