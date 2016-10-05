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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components;

import android.content.Context;

import com.glsebastiany.smartcatalogspl.core.domain.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itempager.ItemPagerActivityBase;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.imagefetching.ImageFetcherIntentService;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.modules.ApplicationModule;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.modules.FirebaseModule;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.main.MainPresenter;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.tabbedgallery.TabbedGalleryController;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.detail.ItemDetailPresenter;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.grid.GalleryGridFragment;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.grid.GalleryGridPresenter;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.detail.ItemDetailFragment;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.itempager.ItemPagerActivity;

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
    ItemUseCases useCases();
    CategoryUseCases categoryUseCases();
    CategoryGroupUseCases categoryGroupUseCases();
    BaseAppDisplayFactory baseAppDisplayFactory();


    void inject(AndroidApplication androidApplication);
    void inject(ImageFetcherIntentService imageFetcherIntentService);

    void inject(MainPresenter mainPresenter);

    void inject(TabbedGalleryController tabbedGalleryController);

    void inject(GalleryGridPresenter galleryGridPresenter);



    void inject(ItemDetailFragment itemDetailFragment);

    void inject(ItemDetailPresenter itemDetailPresenter);

    void inject(GalleryGridFragmentBase<GalleryGridPresenter> galleryGridFragment);

    void inject(ItemPagerActivityBase itemPagerActivityBase);
}
