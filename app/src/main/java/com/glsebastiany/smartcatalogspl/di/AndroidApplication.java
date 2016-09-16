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


import com.glsebastiany.smartcatalogspl.core.presentation.system.ApplicationBase;
import com.glsebastiany.smartcatalogspl.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.di.components.DaggerApplicationComponent;
import com.glsebastiany.smartcatalogspl.di.modules.ApplicationModule;


public class AndroidApplication extends ApplicationBase {

    public static AndroidApplication singleton;

    private ApplicationComponent applicationComponent;

    public AndroidApplication(){
        singleton = this;
    }

    @Override
    protected void setupComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    @Override
    protected void injectComponent() {
        //nothing needs to be injected for now
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
