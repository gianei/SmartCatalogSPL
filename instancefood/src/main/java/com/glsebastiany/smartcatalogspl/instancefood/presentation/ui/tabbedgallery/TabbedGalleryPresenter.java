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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryPresenterBase;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.ApplicationComponent;

import static com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery.TabbedGalleryFragment_.CATEGORIES_ID_EXTRA_ARG;

public class TabbedGalleryPresenter extends TabbedGalleryPresenterBase {

    @Override
    protected void injectMe(TabbedGalleryPresenterBase tabbedGalleryPresenterBase) {
        AndroidApplication.<ApplicationComponent>singleton().getApplicationComponent().inject(this);
    }


    @Nullable
    @Override
    protected String[] getCategoriesIdFrom(Bundle savedState) {
        String[] categoriesIds = null;

        if (savedState!= null) {
            if (savedState.containsKey(CATEGORIES_ID_EXTRA_ARG)) {
                categoriesIds = savedState.getStringArray(CATEGORIES_ID_EXTRA_ARG);
            }
        }
        return categoriesIds;
    }
}
