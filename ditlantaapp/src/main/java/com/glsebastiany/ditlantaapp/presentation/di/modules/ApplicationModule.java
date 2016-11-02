
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

package com.glsebastiany.ditlantaapp.presentation.di.modules;

import android.content.Context;

import com.glsebastiany.ditlantaapp.DitlantaConfigurator;
import com.glsebastiany.ditlantaapp.presentation.di.AndroidApplication;
import com.glsebastiany.ditlantaapp.presentation.ui.AppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.SPLConfigurator;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryRepository;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemPromotedRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.category.CategoryGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.categorygroup.CategoryGroupGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.item.ItemBasicGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.item.ItemPromotedGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.BaseAppDisplayFactory;

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
    ItemBasicRepository provideItemBasicRepository(ItemBasicGreendaoRepository repository){
        return repository;
    }



    //--------------------------

    @Provides
    @Singleton
    ItemPromotedRepository provideItemPromotedRepository(ItemPromotedGreendaoRepository repository){
        return repository;
    }



    //--------------------------

    @Provides
    @Singleton
    CategoryRepository provideCategoryRepository(CategoryGreendaoRepository repository){
        return repository;
    }




    //--------------------------
    @Provides
    @Singleton
    CategoryGroupRepository provideCategoryGroupRepository(CategoryGroupGreendaoRepository repository){
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
    SPLConfigurator provideSPLConfigurator(DitlantaConfigurator configurator){
        return configurator;
    }
}
