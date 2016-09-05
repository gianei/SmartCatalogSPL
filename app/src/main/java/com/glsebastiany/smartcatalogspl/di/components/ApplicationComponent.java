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

package com.glsebastiany.smartcatalogspl.di.components;

import android.content.Context;

import com.glsebastiany.smartcatalogspl.data.CategoryRepository;
import com.glsebastiany.smartcatalogspl.data.ItemRepository;
import com.glsebastiany.smartcatalogspl.di.modules.ApplicationModule;
import com.glsebastiany.smartcatalogspl.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.presentation.controller.BaseGalleryGridController;
import com.glsebastiany.smartcatalogspl.presentation.controller.BaseMainController;
import com.glsebastiany.smartcatalogspl.ui.gallery.grid.FragmentGalleryGrid;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(FragmentGalleryGrid fragmentGalleryGrid);

    //Exposed to sub-graphs.
    Context context();
    ItemRepository itemRepository();
    ItemUseCases useCases();
    CategoryRepository categoryRepository();
    CategoryUseCases categoryUseCases();

    BaseMainController baseMainController();
    BaseGalleryGridController baseGalleryGridController();

    BaseAppDisplayFactory baseAppDisplayFactory();


}
