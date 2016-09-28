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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components;

import android.content.Context;

import com.glsebastiany.smartcatalogspl.core.domain.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseMainController;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseSwipeableController;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.imagefetching.ImageFetcherIntentService;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.modules.ApplicationModule;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.modules.FirebaseModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, FirebaseModule.class})
public interface ApplicationComponent {
    //Exposed to sub-graphs.
    Context context();
    ItemUseCases useCases();
    CategoryUseCases categoryUseCases();
    CategoryGroupUseCases categoryGroupUseCases();
    //BaseSwipeableController baseGalleryGridController();
    BaseAppDisplayFactory baseAppDisplayFactory();


    void inject(AndroidApplication androidApplication);
    void inject(ImageFetcherIntentService imageFetcherIntentService);
}
