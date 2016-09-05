
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

package com.glsebastiany.smartcatalogspl.di.modules;

import android.content.Context;

import com.glsebastiany.smartcatalogspl.data.CategoryRepository;
import com.glsebastiany.smartcatalogspl.data.ItemRepository;
import com.glsebastiany.smartcatalogspl.data.foods.FoodCategoriesRepository;
import com.glsebastiany.smartcatalogspl.data.foods.FoodItemsItemRepository;
import com.glsebastiany.smartcatalogspl.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.di.helper.AppDisplayFactory;
import com.glsebastiany.smartcatalogspl.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.domain.DefaultItemUseCases;
import com.glsebastiany.smartcatalogspl.domain.FoodCategoriesUseCases;
import com.glsebastiany.smartcatalogspl.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.presentation.controller.BaseGalleryGridController;
import com.glsebastiany.smartcatalogspl.presentation.controller.BaseMainController;
import com.glsebastiany.smartcatalogspl.presentationfood.controller.GalleryGridController;
import com.glsebastiany.smartcatalogspl.presentationfood.controller.MainController;

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
    ItemRepository provideItemRepository(FoodItemsItemRepository itemRepository){
        return itemRepository;
    }

    @Provides
    @Singleton
    ItemUseCases provideUseCases(DefaultItemUseCases itemUseCases){
        return itemUseCases;
    }

    @Provides
    @Singleton
    CategoryRepository provideCategoryRepository(FoodCategoriesRepository categoryRepository){
        return categoryRepository;
    }

    @Provides
    @Singleton
    CategoryUseCases provideCategoryUseCases(FoodCategoriesUseCases categoriyUseCases){
        return categoriyUseCases;
    }

    @Provides
    @Singleton
    BaseAppDisplayFactory provideAppDisplayFactory(AppDisplayFactory appDisplayFactory){
        return appDisplayFactory;
    }


    @Provides
    @Singleton
    BaseMainController provideBaseMainController(MainController mainController){
        return mainController;
    }

    @Provides
    @Singleton
    BaseGalleryGridController provideBaseGalleryGridController(GalleryGridController galleryGridController){
        return galleryGridController;
    }
}
