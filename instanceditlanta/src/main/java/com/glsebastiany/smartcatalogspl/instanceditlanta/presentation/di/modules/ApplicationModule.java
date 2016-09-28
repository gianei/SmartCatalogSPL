
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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.modules;

import android.content.Context;

import com.glsebastiany.smartcatalogspl.core.domain.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseMainController;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseSwipeableController;
import com.glsebastiany.smartcatalogspl.instanceditlanta.domain.DitlantaCategoryGroupsRepository;
import com.glsebastiany.smartcatalogspl.instanceditlanta.domain.DitlantaCategoryUseCases;
import com.glsebastiany.smartcatalogspl.instanceditlanta.domain.DitlantaItemUseCases;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.helper.AppDisplayFactory;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.main.MainController;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.tabbedgallery.swipeable.SwipeableController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ItemUseCases provideItemUseCases(DitlantaItemUseCases itemUseCases){
        return itemUseCases;
    }

    @Provides
    @Singleton
    CategoryUseCases provideCategoryUseCases(DitlantaCategoryUseCases categoriyUseCases){
        return categoriyUseCases;
    }

    @Provides
    @Singleton
    CategoryGroupUseCases provideCategoryGroupUseCases(DitlantaCategoryGroupsRepository categoryGroupUseCase){
        return categoryGroupUseCase;
    }

    @Provides
    @Singleton
    BaseAppDisplayFactory provideAppDisplayFactory(AppDisplayFactory appDisplayFactory){
        return appDisplayFactory;
    }
}
