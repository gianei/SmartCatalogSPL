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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.tabbedgallery;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.BaseAdapter;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseTabbedGalleryController;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.Category;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class TabbedGalleryController extends BaseTabbedGalleryController {

    private PagerAdapter pagerAdapter = null;

    @Inject
    CategoryUseCases categoryUseCases;

    @Inject
    FragmentManager fragmentManager;

    @Inject
    BaseAppDisplayFactory baseAppDisplayFactory;

    @Inject
    public TabbedGalleryController(){}

    @Override
    public FragmentStatePagerAdapter getFragmentStatePagerAdapter(Observable<CategoryModel> observable){

        if (pagerAdapter == null)
            pagerAdapter = new PagerAdapter(fragmentManager, observable, baseAppDisplayFactory);

        return pagerAdapter;

    }

    protected Observable<CategoryModel> getCategoryObservable(List<String> categoriesIds) {
        return categoryUseCases.findCategory(categoriesIds);
    }

    @Override
    protected DrawerClickSupport getDrawerClickSupport() {
        return pagerAdapter;
    }

    @Override
    public BaseAdapter getDrawerAdapter(String categoryId) {
        CategoryModel category = categoryUseCases.findCategory(categoryId).toBlocking().single();
        return new DrawerAdapter(context, categoryUseCases.getAllChildren(category), (Category) category);
    }



}
