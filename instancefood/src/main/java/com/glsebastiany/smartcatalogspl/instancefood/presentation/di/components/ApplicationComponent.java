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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components;

import android.content.Context;

import com.glsebastiany.smartcatalogspl.core.domain.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.ItemDetailPresenterBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridPresenterBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itempager.ItemPagerActivityBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.main.MainActivityBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.main.MainPresenterBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.splash.SplashScreenBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryActivityBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryPresenterBase;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.modules.ApplicationModule;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.grid.GalleryGridPresenter;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.main.MainPresenter;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery.TabbedGalleryPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    //Exposed to sub-graphs.
    Context context();
    ItemUseCases useCases();
    CategoryUseCases categoryUseCases();
    CategoryGroupUseCases categoryGroupUseCases();
    BaseAppDisplayFactory baseAppDisplayFactory();


    void inject(MainActivityBase<MainPresenter> activityMain);

    void inject(MainPresenterBase mainPresenter);

    void inject(SplashScreenBase splashScreen);

    void inject(ItemDetailPresenterBase itemDetailPresenter);

    void inject(GalleryGridFragmentBase<GalleryGridPresenter> activityGalleryBase);

    void inject(GalleryGridPresenterBase galleryGridPresenter);

    void inject(TabbedGalleryActivityBase tabbedGalleryBaseActivity);

    void inject(TabbedGalleryPresenterBase tabbedGalleryPresenter);

    void inject(TabbedGalleryFragmentBase<TabbedGalleryPresenter> tabbedGalleryFragmentBase);

    void inject(ItemPagerActivityBase itemPagerActivityBase);
}
