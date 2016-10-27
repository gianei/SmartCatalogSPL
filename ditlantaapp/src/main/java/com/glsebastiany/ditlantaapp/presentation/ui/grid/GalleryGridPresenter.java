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

package com.glsebastiany.ditlantaapp.presentation.ui.grid;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridPresenterBase;
import com.glsebastiany.ditlantaapp.presentation.di.AndroidApplication;
import com.glsebastiany.ditlantaapp.presentation.di.components.ApplicationComponent;

import static com.glsebastiany.ditlantaapp.presentation.ui.grid.GalleryGridFragment_.IS_CATEGORY_ID_QUERY_ARG;
import static com.glsebastiany.ditlantaapp.presentation.ui.grid.GalleryGridFragment_.SEARCH_QUERY_ARG;

public class GalleryGridPresenter extends GalleryGridPresenterBase {

    @Override
    protected void injectMe(GalleryGridPresenterBase galleryGridPresenterBase) {
        AndroidApplication.<ApplicationComponent>singleton().getApplicationComponent().inject(this);
    }

    @Override
    @Nullable
    protected String getQueryFrom(Bundle savedState) {
        String query = null;

        if (savedState!= null) {
            if (savedState.containsKey(SEARCH_QUERY_ARG)) {
                query = savedState.getString(SEARCH_QUERY_ARG);
            }
        }
        return query;
    }

    @Override
    protected boolean getIsCategoryIdQueryFrom(Bundle savedState) {
        boolean isCategoryIdQuery = false;

        if (savedState!= null) {
            if (savedState.containsKey(IS_CATEGORY_ID_QUERY_ARG)) {
                isCategoryIdQuery = savedState.getBoolean(IS_CATEGORY_ID_QUERY_ARG);
            }
        }
        return isCategoryIdQuery;
    }
}
