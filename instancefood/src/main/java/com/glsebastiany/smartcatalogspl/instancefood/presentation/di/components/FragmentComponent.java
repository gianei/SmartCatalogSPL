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


import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseGalleryGridController;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseSwipeableGalleryController;
import com.glsebastiany.smartcatalogspl.core.presentation.di.scopes.PerFragment;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.swipeable.SwipeableGalleryFragmentBase;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.modules.FragmentModule;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = {FragmentModule.class})
public interface FragmentComponent {
    //void inject(BaseFragment baseFragment); // DO NOT INJECT ON BASE CLASSES
    void inject(TabbedGalleryFragmentBase tabbedGalleryFragmentBase);
    void inject(SwipeableGalleryFragmentBase swipeableGalleryFragmentBase);

    //must expose to sub-graphs some classes provided in application
    BaseGalleryGridController baseGalleryGridController();
    BaseAppDisplayFactory baseAppDisplayFactory();
    BaseSwipeableGalleryController baseSwipeableGridController();

}
