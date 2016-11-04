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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.splash;


import com.glsebastiany.smartcatalogspl.core.domain.item.ItemPromotedRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemPromotedUseCases;

import javax.inject.Inject;

public abstract class SplashScreenExtended extends SplashScreenBase{

    @Inject
    public ItemPromotedRepository itemPromotedRepository;

    @Inject
    public ItemPromotedUseCases itemPromotedUseCases;

    protected void injectMe(SplashScreenBase splashScreenBase){
        injectMeInner(splashScreenBase);
        injectMeInner(this);
    }

    protected abstract void injectMeInner(SplashScreenBase splashScreenBase);

    protected abstract void injectMeInner(SplashScreenExtended splashScreen);

    public static SplashScreenExtended getInstance() {

        return (SplashScreenExtended) instance;
    }


    @Override
    protected boolean forceTaskMode() {
        return true;
    }
}
