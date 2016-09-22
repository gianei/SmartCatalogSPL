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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.splash;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.SplashScreenBase;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ActivityComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.DaggerActivityComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.modules.ActivityModule;


public class SplashScreen extends SplashScreenBase {

    ActivityComponent activityComponent;

    @Override
    protected void setupComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void injectComponent() {
        injectMe(this);
    }

    @Override
    protected void injectMe(SplashScreenBase splashScreen) {
        activityComponent.inject(splashScreen);
    }

    public ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication)getApplication()).getApplicationComponent();
    }

}
