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

package com.glsebastiany.smartcatalogspl.domain;

import com.glsebastiany.smartcatalogspl.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.data.CategoryRepository;
import com.glsebastiany.smartcatalogspl.data.foods.FoodCategoryModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class FoodCategoriesUseCases implements CategoryUseCases{

    ItemUseCases itemUseCases;
    CategoryRepository categoryRepository;

    @Inject
    public FoodCategoriesUseCases(ItemUseCases itemUseCases, CategoryRepository categoryRepository){
        this.itemUseCases = itemUseCases;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Observable<CategoryModel> mainViewCategories() {
        return categoryRepository.getDirectChildren(new FoodCategoryModel("Root"));
    }

    @Override
    public Observable<CategoryModel> drawerCategories() {
        return categoryRepository.getAll();
    }

}
