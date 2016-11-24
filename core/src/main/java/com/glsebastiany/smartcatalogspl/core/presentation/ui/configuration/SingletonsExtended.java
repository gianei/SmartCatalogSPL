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


import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryUseCasesExtended;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemExtendedRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemExtendedUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.splash.SplashScreenBase;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class SingletonsExtended extends Singletons {

    public interface InjectorExtended{
        void inject(Singletons singletons);
        void inject(SingletonsExtended singletonsExtended);
    }

    @Inject
    public ItemExtendedRepository itemExtendedRepository;

    @Inject
    public ItemExtendedUseCases itemExtendedUseCases;

    public static SingletonsExtended start(InjectorExtended injector) {
        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (SplashScreenBase.class) {
                if (instance == null) {
                    instance = new SingletonsExtended();
                    injector.inject(instance);
                    injector.inject((SingletonsExtended) instance);
                }
            }
        }
        return (SingletonsExtended)instance;
    }

    public static SingletonsExtended getInstance() {
        if (instance == null) {
            throw new RuntimeException("It has not been injected");
        }

        return (SingletonsExtended) instance;
    }


}
