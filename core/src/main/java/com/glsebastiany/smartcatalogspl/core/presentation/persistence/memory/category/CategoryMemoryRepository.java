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

package com.glsebastiany.smartcatalogspl.core.presentation.persistence.memory.category;

import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CategoryMemoryRepository implements CategoryRepository {

    List<CategoryModel> categories = new LinkedList<>();

    @Inject
    public CategoryMemoryRepository(){
        MemoryCategoryModel alcoholic = new MemoryCategoryModel("Alcoholic", "Drinks", "Alcoholic", new ArrayList<CategoryModel>());
        categories.add(alcoholic);
        MemoryCategoryModel juice = new MemoryCategoryModel("Juice", "Drinks", "Juice", new ArrayList<CategoryModel>());
        categories.add(juice);
        ArrayList<CategoryModel> drinksCats = new ArrayList<>();
        drinksCats.add(alcoholic);
        drinksCats.add(juice);

        MemoryCategoryModel drinks = new MemoryCategoryModel("Drinks", CategoryModel.ROOT_ID.toString(), "Drinks", drinksCats);
        categories.add(drinks);


        MemoryCategoryModel pasta = new MemoryCategoryModel("Pasta", "Breakfest", "Pasta", new ArrayList<CategoryModel>());
        categories.add(pasta);
        MemoryCategoryModel sweet = new MemoryCategoryModel("Sweet", "Breakfest", "Sweet", new ArrayList<CategoryModel>());
        categories.add(sweet);
        ArrayList<CategoryModel> breakfestCats = new ArrayList<>();
        breakfestCats.add(pasta);
        breakfestCats.add(sweet);

        MemoryCategoryModel breakfest = new MemoryCategoryModel("Breakfest", CategoryModel.ROOT_ID.toString(), "Breakfest", breakfestCats);
        categories.add(breakfest);

        MemoryCategoryModel android = new MemoryCategoryModel("Android", CategoryModel.ROOT_ID.toString(), "Android", new ArrayList<CategoryModel>());
        categories.add(android);

        ArrayList<CategoryModel> rootCats = new ArrayList<>();
        rootCats.add(drinks);
        rootCats.add(breakfest);
        rootCats.add(android);
        categories.add(new MemoryCategoryModel(CategoryModel.ROOT_ID.toString(), null, "Root", rootCats));

    }

    @Override
    public List<? extends CategoryModel> loadAll() {
        return categories;
    }

    @Override
    public CategoryModel load(String categoryId) {
        for (CategoryModel category :
                categories) {
            if (category.getStringId().equals(categoryId))
                return category;
        }
        return null;
    }

    @Override
    public List<? extends CategoryModel> load(List<String> categoriesId) {
        List<CategoryModel> returnItems = new LinkedList<>();
        for (CategoryModel category :
                categories) {
            for (String string: categoriesId) {
                if (category.getStringId().contains(string))
                    returnItems.add(category);
            }
        }

        return returnItems;
    }

    @Override
    public void insert(CategoryModel categoryModel) {
        categories.add(categoryModel);
    }

    @Override
    public void insertAll(List<CategoryModel> categoryList) {
        for (CategoryModel category :
                categoryList) {
            categories.add(category);
        }
    }

    @Override
    public void remove(String categoryId) {
        for (Iterator<CategoryModel> iterator = categories.iterator(); iterator.hasNext();) {
            CategoryModel category = iterator.next();
            if (category.getStringId().equals(categoryId)) {
                iterator.remove();
                return;
            }
        }
    }

    @Override
    public void removeAll() {
        categories.clear();
    }
}
