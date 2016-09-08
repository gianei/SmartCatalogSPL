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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.main;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.data.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseMainController;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

public class MainController extends BaseMainController {

    @Inject
    CategoryGroupUseCases categoryGroupUseCases;

    @Inject
    BaseAppDisplayFactory baseAppDisplayFactory;

    @Inject
    public MainController(){}

    public void setupRecyclerView(Context context, final ProgressBar progressBar, final RecyclerView recyclerView){
        Observable<CategoryGroupModel> observable = categoryGroupUseCases.mainViewCategoriesGroups();

        endSubscriptions();
        addSubscription(observable.subscribe(new Observer<CategoryGroupModel>() {
            @Override
            public void onCompleted() {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CategoryGroupModel categoryGroupModel) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }));

        recyclerView.setLayoutManager(
                new GridLayoutManager(
                        context,
                        context.getResources().getInteger(R.integer.grid_span_start),
                        LinearLayoutManager.VERTICAL, false
                )
        );

        recyclerView.setAdapter(new MainAdapter(context, observable, baseAppDisplayFactory));

    }
}
