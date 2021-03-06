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

package com.glsebastiany.smartcatalogspl.core.presentation.di;

import android.app.Application;

public abstract class InjectableApplication<C> extends Application {

    private static InjectableApplication singleton;

    public static <C> InjectableApplication<C> singleton() {
        //noinspection unchecked
        return singleton;
    }

    private C applicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        singleton = this;

        applicationComponent = setupApplicationComponent();
        injectComponent();

    }

    protected abstract C setupApplicationComponent();

    protected abstract void injectComponent();

    public C getApplicationComponent() {
        return applicationComponent;
    }

}
