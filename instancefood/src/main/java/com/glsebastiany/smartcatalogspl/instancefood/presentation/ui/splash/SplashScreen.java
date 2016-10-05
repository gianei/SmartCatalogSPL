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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.splash;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.splash.SplashScreenBase;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.ActivityComponent;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.DaggerActivityComponent;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.modules.ActivityModule;


public class SplashScreen extends SplashScreenBase {


    @Override
    protected void injectMe(SplashScreenBase splashScreen) {
        AndroidApplication.<ApplicationComponent>singleton().getApplicationComponent().inject(splashScreen);
    }

    public ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication)getApplication()).getApplicationComponent();
    }

}
