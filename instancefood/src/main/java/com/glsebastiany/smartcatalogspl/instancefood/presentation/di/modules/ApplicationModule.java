
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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.di.modules;

import android.content.Context;
import android.support.annotation.Nullable;

import com.glsebastiany.smartcatalogspl.core.SPLConfigurator;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemPromotedModel;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryRepository;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupRepository;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemPromotedRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.category.CategoryGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.categorygroup.CategoryGroupGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.item.ItemBasicGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.item.ItemPromotedGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.instancefood.FoodConfigurator;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.AppDisplayFactory;
import com.glsebastiany.smartcatalogspl.instancefood.memory.category.CategoryMemoryRepository;
import com.glsebastiany.smartcatalogspl.instancefood.memory.categorygroup.CategoryGroupMemoryRepository;
import com.glsebastiany.smartcatalogspl.instancefood.memory.item.ItemBasicMemoryRepository;

import java.util.List;

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

    //--------------------------

    @Provides
    @Singleton
    ItemBasicRepository provideItemBasicRepository(ItemBasicMemoryRepository repository){
        return repository;
    }



    //--------------------------

    @Provides
    @Singleton
    ItemPromotedRepository provideItemPromotedRepository( ){
        //TODO make a stub
        return new ItemPromotedRepository() {
            @Override
            public List<? extends ItemPromotedModel> loadAll() {
                return null;
            }

            @Override
            public ItemPromotedModel load(String itemId) {
                return null;
            }

            @Override
            public void insert(ItemPromotedModel itemPromotedModel) {

            }

            @Override
            public void insertAll(List<ItemPromotedModel> itemPromotedList) {

            }

            @Override
            public void remove(String itemId) {

            }

            @Override
            public void removeAll() {

            }
        };
    }



    //--------------------------

    @Provides
    @Singleton
    CategoryRepository provideCategoryRepository(CategoryMemoryRepository repository){
        return repository;
    }




    //--------------------------
    @Provides
    @Singleton
    CategoryGroupRepository provideCategoryGroupRepository(CategoryGroupMemoryRepository repository){
        return repository;
    }





    //--------------------------
    @Provides
    @Singleton
    BaseAppDisplayFactory provideAppDisplayFactory(AppDisplayFactory appDisplayFactory){
        return appDisplayFactory;
    }

    //--------------------------
    @Provides
    @Singleton
    SPLConfigurator provideSPLConfigurator(FoodConfigurator configurator){
        return configurator;
    }


}
