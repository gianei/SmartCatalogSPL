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
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewStub;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.data.ItemModel;

import rx.Observable;

public abstract class BaseSwipeableController extends BaseSubscriptionedController {
    public abstract Observable<ItemModel> getItemsObservable(String categoryId);
    public abstract void setupRecyclerView(Context context, Observable<ItemModel> itemModelObservable, ProgressBar progressBar, RecyclerView recyclerView, FragmentManager fragmentManager);
    public abstract void inflateItemDetailStub(ViewStub viewStub, ItemModel itemModel);
}
