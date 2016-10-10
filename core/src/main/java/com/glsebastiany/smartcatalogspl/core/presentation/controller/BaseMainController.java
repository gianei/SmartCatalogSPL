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

package com.glsebastiany.smartcatalogspl.core.presentation.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.data.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;

import rx.Observable;
import rx.Observer;

public abstract class BaseMainController extends BaseSubscriptionedController{

    protected Context context;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    public void bindAndSetup(Context context, ProgressBar progressBar, RecyclerView recyclerView){
        this.context = context;
        this.progressBar = progressBar;
        this.recyclerView = recyclerView;

        setupRecyclerView();
    }

    private void setupRecyclerView(){
        Observable<CategoryGroupModel> observable = ObservableHelper.setupThreads(getCategoryGroupObservable().cache());

        addSubscription(observable.subscribe(new Observer<CategoryGroupModel>() {
            @Override
            public void onCompleted() {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException(e);
            }

            @Override
            public void onNext(CategoryGroupModel categoryGroupModel) {
            }
        }));

        recyclerView.setLayoutManager(
                new GridLayoutManager(
                        context,
                        context.getResources().getInteger(R.integer.grid_span_start),
                        LinearLayoutManager.VERTICAL, false
                )
        );

        recyclerView.setAdapter(getRecyclerViewAdapter(observable));

    }

    @NonNull
    protected abstract RecyclerView.Adapter<? extends RecyclerView.ViewHolder> getRecyclerViewAdapter(Observable<CategoryGroupModel> observable);

    protected abstract Observable<CategoryGroupModel> getCategoryGroupObservable();
}
