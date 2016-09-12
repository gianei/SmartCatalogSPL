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

package com.glsebastiany.smartcatalogspl.di.components.fragment;


import com.glsebastiany.smartcatalogspl.di.modules.ItemsGroupModule;
import com.glsebastiany.smartcatalogspl.core.presentation.scopes.PerItemsGroup;
import com.glsebastiany.smartcatalogspl.ui.tabbedgallery.swipeable.detail.FragmentItemDetail;
import com.glsebastiany.smartcatalogspl.ui.tabbedgallery.swipeable.detail.FragmentItemPager;
import com.glsebastiany.smartcatalogspl.ui.tabbedgallery.swipeable.grid.FragmentGalleryGrid;

import dagger.Component;

@PerItemsGroup
@Component(dependencies = FragmentComponent.class, modules = ItemsGroupModule.class)
public interface ItemsGroupComponent {
    void inject(FragmentGalleryGrid fragmentGalleryGrid);
    void inject(FragmentItemPager fragmentItemPager);
    void inject(FragmentItemDetail fragmentItemDetail);
}
