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
import android.support.v7.app.AppCompatActivity;

import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryRepository;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupRepository;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.Utils;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;


public abstract class SplashScreenBase extends AppCompatActivity {

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

    @Inject
    FirebaseAuth.AuthStateListener authStateListener;

    protected static SplashScreenBase instance = null;

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

        baseAppDisplayFactory.startHomeScreen(this);
        FirebaseAuth auth =  FirebaseAuth.getInstance();

        auth.addAuthStateListener(authStateListener);
    }

    protected abstract void injectMe(SplashScreenBase splashScreen);

    protected boolean forceTaskMode() {
        return true;
    }

    public static SplashScreenBase getInstance() {

        return instance;
    }

}
