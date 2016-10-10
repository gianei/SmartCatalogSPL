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


import android.content.Context;
import android.content.Intent;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryActivityBase;
import com.glsebastiany.smartcatalogspl.instancefood.R;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.ApplicationComponent;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;

import java.util.List;

@EActivity(R.layout.activity_gallery)
public class TabbedGalleryActivity extends TabbedGalleryActivityBase {

    public static void start(Context context, List<String> categoriesIds ){
        TabbedGalleryActivity_
                .intent(context)
                .categoriesIds(categoriesIds.toArray(new String[categoriesIds.size()]))
                .start();
    }

    @Override
    protected void injectMe(TabbedGalleryActivityBase tabbedGalleryBaseActivity) {
        AndroidApplication.<ApplicationComponent>singleton().getApplicationComponent().inject(tabbedGalleryBaseActivity);
    }


}