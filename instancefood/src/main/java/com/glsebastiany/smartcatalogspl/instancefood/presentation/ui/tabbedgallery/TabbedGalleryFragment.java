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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery;

import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryFragmentBase;
import com.glsebastiany.smartcatalogspl.instancefood.R;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.DaggerFragmentComponent;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.FragmentComponent;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.modules.FragmentModule;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_gallery)
public class TabbedGalleryFragment extends TabbedGalleryFragmentBase {

    FragmentComponent fragmentComponent;

    public static TabbedGalleryFragment newInstance(String[] categoriesId){
        return FragmentGallery_.builder().categoriesIdExtra(categoriesId).build();
    }

    @Override
    protected void setupComponent() {

        fragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(AndroidApplication.singleton.getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    protected void injectMe(TabbedGalleryFragmentBase tabbedGalleryFragmentBase) {
        fragmentComponent.inject(tabbedGalleryFragmentBase);
    }


}
