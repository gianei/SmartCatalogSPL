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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.data.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseMainController;
import com.glsebastiany.smartcatalogspl.instancefood.R;

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

    @NonNull
    @Override
    protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> getRecyclerViewAdapter(Observable<CategoryGroupModel> observable) {
        return new MainAdapter(context, observable, baseAppDisplayFactory);
    }

    @Override
    protected Observable<CategoryGroupModel> getCategoryGroupObservable() {
        return categoryGroupUseCases.mainViewCategoriesGroups();
    }
}
