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

package com.glsebastiany.ditlantaapp.presentation.ui.splash;

import com.glsebastiany.ditlantaapp.presentation.di.components.DaggerApplicationComponent;
import com.glsebastiany.ditlantaapp.presentation.di.modules.ApplicationModule;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.Singletons;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.SingletonsExtended;
import com.glsebastiany.ditlantaapp.presentation.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.splash.SplashScreenExtended;


public class SplashScreen extends SplashScreenExtended {

    private static ApplicationComponent applicationComponent;

    @Override
    protected void injectMeInner(Singletons singletons) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplication().getApplicationContext()))
                .build();

        applicationComponent.inject(singletons);

    }

    @Override
    protected void injectMeInner(SingletonsExtended singletonsExtended) {
        applicationComponent.inject(singletonsExtended);

    }

}
