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

package com.glsebastiany.ditlantaapp.presentation.di.components;

import android.content.Context;

import com.glsebastiany.ditlantaapp.presentation.ui.grid.GalleryGridFragment;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.di.InjectableApplication;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.ItemDetailPresenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itempager.ItemPagerActivity;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.main.MainPresenterBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.splash.SplashScreenBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.splash.SplashScreenExtended;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryActivityBase;
import com.glsebastiany.ditlantaapp.data.imagefetching.ImageFetcherIntentService;
import com.glsebastiany.ditlantaapp.presentation.di.AndroidApplication;
import com.glsebastiany.ditlantaapp.presentation.di.modules.ApplicationModule;
import com.glsebastiany.ditlantaapp.presentation.di.modules.FirebaseModule;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridPresenter;
import com.glsebastiany.ditlantaapp.presentation.ui.login.LoginActivity;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.search.SearchActivity;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryPresenterBase;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, FirebaseModule.class})
public interface ApplicationComponent {
    //Exposed to sub-graphs.
    Context context();
    ItemBasicUseCases useCases();
    CategoryUseCases categoryUseCases();
    CategoryGroupUseCases categoryGroupUseCases();
    BaseAppDisplayFactory baseAppDisplayFactory();


    void inject(AndroidApplication androidApplication);
    void inject(ImageFetcherIntentService imageFetcherIntentService);

    void inject(MainPresenterBase mainPresenter);

    void inject(TabbedGalleryPresenterBase tabbedGalleryPresenter);

    void inject(GalleryGridPresenter galleryGridPresenter);

    void inject(ItemDetailPresenter itemDetailPresenter);

    void inject(GalleryGridFragment galleryGridFragment);

    void inject(ItemPagerActivity itemPagerActivity);

    void inject(TabbedGalleryActivityBase tabbedGalleryBaseActivity);

    void inject(SplashScreenBase splashScreen);

    void inject(SplashScreenExtended splashScreen);

    void inject(LoginActivity loginActivity);

    void inject(SearchActivity searchActivity);

    void inject(InjectableApplication injectableApplication);
}
