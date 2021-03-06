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
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.core.presentation.widget.SpacesItemDecoration;

import rx.Observable;
import rx.Observer;

public abstract class BaseGalleryGridController extends BaseSubscriptionedController {
    protected Context context;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FragmentManager fragmentManager;
    private Observable<ItemModel> observable;

    private GridLayoutManager gridLayoutManager;

    @NonNull
    protected abstract RecyclerView.Adapter<RecyclerView.ViewHolder>
        getRecyclerViewAdapter(Observable<ItemModel> observable, FragmentManager fragmentManager);




}
