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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.di.modules;

import android.support.v4.app.FragmentManager;

import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseTabbedGalleryController;
import com.glsebastiany.smartcatalogspl.core.presentation.system.FragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.di.PerFragment;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery.TabbedGalleryController;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private final FragmentBase fragment;

    public FragmentModule(FragmentBase fragment) {
        this.fragment = fragment;
    }

    @Provides
    @PerFragment
    FragmentBase fragment() {
        return this.fragment;
    }

    @Provides
    @PerFragment
    FragmentManager provideFragmentManager() { return fragment.getChildFragmentManager();}

    @Provides
    @PerFragment
    BaseTabbedGalleryController provideBaseGalleryController(TabbedGalleryController tabbedGalleryController){
        return tabbedGalleryController;
    }
}
