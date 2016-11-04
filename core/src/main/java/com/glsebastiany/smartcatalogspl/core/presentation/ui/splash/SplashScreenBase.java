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

import android.os.Bundle;

import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryRepository;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupRepository;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.di.InjectableActivity;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.Utils;

import javax.inject.Inject;


public abstract class SplashScreenBase extends InjectableActivity {

    protected static SplashScreenBase instance = null;

    //@Inject
    //BaseAppDisplayFactory baseAppDisplayFactory;

    private static int sessionDepth = 0;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (SplashScreenBase.class) {
                if (instance == null) {
                    instance = this;
                    instance.injectMe(instance);
                }
            }
        }




        if (sessionDepth++ == 0 && forceTaskMode())
            Utils.startLockTasMode(this);

        baseAppDisplayFactory.startMainActivity(this);
    }

    @Override
    protected void injectApplicationComponent() {
        //baseAppDisplayFactory = InjectableApplication.singleton().baseAppDisplayFactory;
        //injectMe(this);
    }

    protected abstract void injectMe(SplashScreenBase splashScreen);

    protected abstract boolean forceTaskMode();







    @Inject
    public ItemBasicRepository itemBasicRepository;

    @Inject
    public CategoryRepository categoryRepository;

    @Inject
    public CategoryGroupRepository categoryGroupRepository;

    @Inject
    public BaseAppDisplayFactory baseAppDisplayFactory;

    @Inject
    public ItemBasicUseCases itemBasicUseCases;

    @Inject
    public CategoryUseCases categoryUseCases;

    @Inject
    public CategoryGroupUseCases categoryGroupUseCases;



    public static SplashScreenBase getInstance() {

        return instance;
    }

}
