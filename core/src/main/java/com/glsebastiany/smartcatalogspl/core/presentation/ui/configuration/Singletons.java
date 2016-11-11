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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration;

import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryRepository;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupRepository;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.images.ImagesHelperBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.splash.SplashScreenBase;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class Singletons {

    public interface Injector{
        void inject(Singletons singletons);
    }

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
    public FirebaseAuth.AuthStateListener authStateListener;

    @Inject
    public ImagesHelperBase imagesHelperBase;

    protected static Singletons instance = null;

    public static Singletons start(Injector injector) {
        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (SplashScreenBase.class) {
                if (instance == null) {
                    instance = new Singletons();
                    injector.inject(instance);
                }
            }
        }
        return instance;
    }

    public static Singletons getInstance() {
        if (instance == null) {
            throw new RuntimeException("It has not been injected");
        }

        return instance;
    }

}
