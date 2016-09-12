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

package com.glsebastiany.smartcatalogspl.di;


import android.app.Application;

import com.glsebastiany.smartcatalogspl.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.di.components.DaggerApplicationComponent;
import com.glsebastiany.smartcatalogspl.di.modules.ApplicationModule;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.dimodules.FirebaseModule;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;


public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    //private ItemsGalleryComponent itemsGalleryComponent;

    @Inject
    FirebaseAuth.AuthStateListener authStateListener;

    @Override public void onCreate() {
        super.onCreate();
        this.initializeInjector();

        applicationComponent.inject(this);

        FirebaseAuth auth =  FirebaseAuth.getInstance();

        auth.addAuthStateListener(authStateListener);
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .firebaseModule(new FirebaseModule())
                //.updateModule(new UpdateModule())
                .build();

    }

    /**
     * Used to mantain scope of Gallery that is bigger than the activity scope <br>
     * See more at <a href="http://frogermcs.github.io/dependency-injection-with-dagger-2-custom-scopes">CustomScopes</a>
     */
    /*public ItemsGalleryComponent createItemsGalleryComponent(){
        itemsGalleryComponent = DaggerItemsGalleryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .itemsModule(new ItemsModule())
                .build();

        return itemsGalleryComponent;
    }*/

    /*public ItemsGalleryComponent getItemsGalleryComponent(){
        return itemsGalleryComponent;
    }*/

    /*public void releaseGalleryComponent(){
        itemsGalleryComponent = null;
    }*/

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
