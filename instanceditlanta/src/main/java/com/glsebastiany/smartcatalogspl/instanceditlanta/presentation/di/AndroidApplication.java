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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di;


import com.glsebastiany.smartcatalogspl.core.presentation.system.BetterApplicationBase;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.DaggerApplicationComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.modules.ApplicationModule;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;


public class AndroidApplication extends BetterApplicationBase<ApplicationComponent> {



    @Inject
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected ApplicationComponent setupApplicationComponent() {

        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    @Override
    protected void injectComponent() {
        getApplicationComponent().inject(this);

        FirebaseAuth auth =  FirebaseAuth.getInstance();

        auth.addAuthStateListener(authStateListener);
    }

}
