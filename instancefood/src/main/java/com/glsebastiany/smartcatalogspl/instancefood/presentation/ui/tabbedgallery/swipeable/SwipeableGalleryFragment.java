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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.tabbedgallery.swipeable;

import com.glsebastiany.smartcatalogspl.instancefood.R;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.DaggerFragmentComponent;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.DaggerItemsGroupComponent;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.FragmentComponent;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.ItemsGroupComponent;
import com.glsebastiany.smartcatalogspl.core.presentation.di.HasComponent;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.modules.FragmentModule;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.modules.ItemsGroupModule;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_gallery_visualization)
public class SwipeableGalleryFragment extends SwipeableGalleryFragmentBase implements HasComponent<ItemsGroupComponent> {

    private ItemsGroupComponent itemsGroupComponent;

    FragmentComponent fragmentComponent;

    public static SwipeableGalleryFragment newInstance(String categoryId) {
        return SwipeableGalleryFragment_.builder().categoryId(categoryId).build();
    }

    protected void innerAfterInject(){
        itemsGroupComponent = DaggerItemsGroupComponent.builder()
                .fragmentComponent(fragmentComponent)
                .itemsGroupModule(
                        new ItemsGroupModule(itemObservableSerializable.getItemsObservable())
                )
                .build();
    }

    @Override
    protected void setupComponent() {

        fragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(AndroidApplication.singleton.getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    protected void injectMe(SwipeableGalleryFragmentBase swipeableGalleryFragmentBase) {
        fragmentComponent.inject(swipeableGalleryFragmentBase);
    }

    public ItemsGroupComponent getComponent() {
        return itemsGroupComponent;
    }


}
