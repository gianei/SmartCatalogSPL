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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.glsebastiany.smartcatalogspl.core.data.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.BaseAppDisplayFactory;

public abstract class MainAdapterBase<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected final Context context;
    protected final BaseAppDisplayFactory baseAppDisplayFactory;

    public MainAdapterBase(Context context, BaseAppDisplayFactory baseAppDisplayFactory) {
        this.context = context;
        this.baseAppDisplayFactory = baseAppDisplayFactory;
    }

    public abstract void addItem(CategoryGroupModel categoryGroupModel);

}
