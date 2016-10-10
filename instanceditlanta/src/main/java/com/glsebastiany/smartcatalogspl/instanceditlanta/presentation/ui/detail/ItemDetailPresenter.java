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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.ItemDetailPresenterBase;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ApplicationComponent;

public class ItemDetailPresenter extends ItemDetailPresenterBase {

    @Override
    protected void injectMe(ItemDetailPresenterBase itemDetailPresenterBase) {
        AndroidApplication.<ApplicationComponent>singleton().getApplicationComponent().inject(this);
    }

    @Override
    @Nullable
    protected String getCategoryIdFrom(Bundle savedState) {
        String categoryId = null;

        if (savedState!= null) {
            if (savedState.containsKey(ItemDetailFragment_.ITEM_ID_ARG)) {
                categoryId = savedState.getString(ItemDetailFragment_.ITEM_ID_ARG);
            }
        }
        return categoryId;
    }
}
