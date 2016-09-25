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


import com.glsebastiany.smartcatalogspl.core.presentation.di.scopes.PerItemsGroup;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.swipeable.detail.FragmentItemDetailBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.swipeable.detail.FragmentItemPagerBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.swipeable.grid.FragmentGalleryGridBase;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.modules.ItemsGroupModule;

import dagger.Component;

@PerItemsGroup
@Component(dependencies = FragmentComponent.class, modules = ItemsGroupModule.class)
public interface ItemsGroupComponent {
    void inject(FragmentGalleryGridBase activityGalleryBase);
    void inject(FragmentItemPagerBase fragmentItemPager);
    void inject(FragmentItemDetailBase fragmentItemDetail);
}