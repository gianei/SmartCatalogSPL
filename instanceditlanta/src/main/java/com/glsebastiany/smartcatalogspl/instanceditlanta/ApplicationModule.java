
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

import com.glsebastiany.ditlantaapp.presentation.ui.AppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryRepository;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemPromotedRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.category.CategoryGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.categorygroup.CategoryGroupGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.item.ItemBasicGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.item.ItemPromotedGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.AppDisplayFactoryBasic;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.BaseAppDisplayFactory;
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

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.context;
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
    BaseAppDisplayFactory provideAppDisplayFactory(AppDisplayFactoryBasic appDisplayFactory){
        return appDisplayFactory;
    }




    @Provides @Singleton
    public DatabaseReference provideFirebase(){
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);

        DatabaseReference firebase = FirebaseDatabase.getInstance().getReferenceFromUrl(BuildConfig.FIREBASE_URL);
        //config.setPersistenceCacheSizeBytes(50 * 1000 * 1000);
        // It is not FirebaseDatabase.getInstance().getReference();
        // to allow url to be changed on compile time

        // keep synced to persist always and not be purged when cache > 10mb
        //firebase.child(FirebaseSuitCase.LOCATION).keepSynced(true);
        return firebase;
    }

    @Provides @Singleton
    public FirebaseAuth.AuthStateListener authStateListener(LoginAuthStateListener authStateListener){
        return authStateListener;
    }

}
