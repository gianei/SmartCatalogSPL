
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

package com.glsebastiany.smartcatalogspl.instanceditlanta;

import android.content.Context;

import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryRepository;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemExtendedRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.category.CategoryGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.categorygroup.CategoryGroupGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.item.ItemBasicGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.item.ItemExtendedGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.AppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.home.GategoryGroupsHome;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.itemdetail.ListDetail;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.itemdetail.ListExtendedDetail;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.itemdetail.SwipeListExtendedDetail;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.itemsets.VerticalItemExtendedSet;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.itemsets.VerticalItemSet;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.login.LoginAuthStateListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

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
    ItemBasicRepository provideItemBasicRepository(ItemBasicGreendaoRepository repository){
        return repository;
    }

    //Persistence for Category model
    @Provides @Singleton
    CategoryRepository provideCategoryRepository(CategoryGreendaoRepository repository){
        return repository;
    }

    //Persistence for CategoryGroup model
    @Provides @Singleton
    CategoryGroupRepository provideCategoryGroupRepository(CategoryGroupGreendaoRepository repository){
        return repository;
    }

    //Helper container with options about UI
    @Provides @Singleton
    BaseAppDisplayFactory provideAppDisplayFactory(AppDisplayFactory appDisplayFactory){
        return appDisplayFactory;
    }

    //External sync
    @Provides @Singleton
    public DatabaseReference provideFirebase(){
        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReferenceFromUrl(BuildConfig.FIREBASE_URL);
        return firebase;
    }

    //External sync authentication
    @Provides @Singleton
    public FirebaseAuth.AuthStateListener authStateListener(LoginAuthStateListener authStateListener){
        return authStateListener;
    }



    //--------------------------
    //Optional feature

    // Must be provided only if Extended feature is selected
    @Provides @Singleton
    ItemExtendedRepository provideItemPromotedRepository(ItemExtendedGreendaoRepository repository){
        return repository;
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
    AppDisplayFactory.ItemDetailConfigurator itemDetailConfigurator(SwipeListExtendedDetail itemDetailConfigurator){
        return itemDetailConfigurator;
    }

    // Extended && GridZoomable -> GridZoomItemExtendedSet
    // Extended && Vertical -> VerticalItemExtendedSet
    // !Extended && GridZoomable -> GridZoomItemSet
    // !Extended && Vertical -> VerticalItemSet
    @Provides @Singleton
    AppDisplayFactory.ItemSetsConfigurator itemSetsConfigurator(VerticalItemExtendedSet itemSetsConfigurator) {
        return itemSetsConfigurator;
    }
}
