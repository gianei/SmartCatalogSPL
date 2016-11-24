
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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.di;

import android.content.Context;

import com.glsebastiany.smartcatalogspl.core.domain.category.BaseCategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryRepository;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.authentication.Authenticator;
import com.glsebastiany.smartcatalogspl.core.presentation.authentication.NoAuthenticator;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.memory.category.CategoryMemoryRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.memory.categorygroup.CategoryGroupMemoryRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.memory.item.ItemBasicMemoryRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.AppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.home.GategoryGroupsHome;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.images.WebImagesHelper;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.itemdetail.SwipeListDetail;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.itemsets.GridZoomItemSet;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.menus.EmptyMenu;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.toolbar.DefaultToolbar;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    //--------------------------
    //mandatory provides

    @Provides  @Singleton
    Context provideApplicationContext() {
        return this.context;
    }

    //Persistence for Item model
    @Provides @Singleton
    ItemBasicRepository provideItemBasicRepository(ItemBasicMemoryRepository repository){
        return repository;
    }

    //Persistence for Category model
    @Provides @Singleton
    CategoryRepository provideCategoryRepository(CategoryMemoryRepository repository){
        return repository;
    }

    @Provides @Singleton
    BaseCategoryUseCases provideBaseCategoryUseCases(CategoryUseCases categoryUseCases){
        return categoryUseCases;
    }

    //Persistence for CategoryGroup model
    @Provides @Singleton
    CategoryGroupRepository provideCategoryGroupRepository(CategoryGroupMemoryRepository repository){
        return repository;
    }

    //Helper container with options about UI
    @Provides @Singleton
    BaseAppDisplayFactory provideAppDisplayFactory(AppDisplayFactory appDisplayFactory){
        return appDisplayFactory;
    }

    @Provides @Singleton
    Authenticator provideAuthenticator(NoAuthenticator authenticator){
        return authenticator;
    }

    //--------------------------
    // Choice features

    // CategoryGroups -> CategoryGroupsHome
    // CategoryPages -> CategoryPagesHome
    @Provides @Singleton
    AppDisplayFactory.HomeScreenConfigurator homeScreenConfigurator(GategoryGroupsHome homeScreenConfigurator){
        return homeScreenConfigurator;
    }

    // Extended && SwipeToNextItem - > SwipeListExtendedDetail
    // Extended && !SwipeToNexItem  - > ListExtendedDetail
    // !Extended && SwipeToNexItem - > SwipeListDetail
    // !Extended && !SwipeToNexItem - > ListDetail
    @Provides @Singleton
    AppDisplayFactory.ItemDetailConfigurator itemDetailConfigurator(SwipeListDetail itemDetailConfigurator){
        return itemDetailConfigurator;
    }

    // Extended && GridZoomable -> GridZoomItemExtendedSet
    // Extended && Vertical -> VerticalItemExtendedSet
    // !Extended && GridZoomable -> GridZoomItemSet
    // !Extended && Vertical -> VerticalItemSet
    @Provides @Singleton
    AppDisplayFactory.ItemSetsConfigurator itemSetsConfigurator(GridZoomItemSet itemSetsConfigurator) {
        return itemSetsConfigurator;
    }

    @Provides @Singleton
    BaseAppDisplayFactory.ToolbarConfigurator toolbarConfiguratior(DefaultToolbar toolbarConfigurator){
        return toolbarConfigurator;
    }

    @Provides @Singleton
    BaseAppDisplayFactory.MenuConfigurator menuConfigurator(EmptyMenu menuConfigurator){
        return menuConfigurator;
    }

    @Provides @Singleton
    BaseAppDisplayFactory.ImagesHelper imagesHelper(){
        return new WebImagesHelper();
    }

}
