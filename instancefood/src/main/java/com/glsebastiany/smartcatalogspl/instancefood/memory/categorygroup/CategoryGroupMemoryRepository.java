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

package com.glsebastiany.smartcatalogspl.instancefood.memory.categorygroup;

import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.data.categorygroup.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryRepository;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupRepository;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.splash.SplashScreenBase;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.ApplicationComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class CategoryGroupMemoryRepository implements CategoryGroupRepository {

    List<CategoryGroupModel> categoryGroups = new LinkedList<>();

    private boolean started = false;


    @Inject
    public CategoryGroupMemoryRepository(){
    }

    @Override
    public List<? extends CategoryGroupModel> loadAll() {
        if (!started){
            CategoryRepository categoryRepository = Singletons.getInstance().categoryRepository;

            List<? extends CategoryModel> foodCats = categoryRepository.load( new ArrayList<>(Arrays.asList("Pasta", "Breakfest")));
            categoryGroups.add(
                    new MemoryCategoryGroupEntity("0", "Food", "https://unsplash.it/300?random",
                            foodCats
                    )
            );

            List<? extends CategoryModel> liquidCats = categoryRepository.load( new ArrayList<>(Arrays.asList("Drinks", "Alcoholic", "Juice")));

            categoryGroups.add(
                    new MemoryCategoryGroupEntity("1", "Liquids", "https://unsplash.it/300?random",
                            liquidCats
                    )
            );

            List<? extends CategoryModel> root = categoryRepository.load( new ArrayList<>(Arrays.asList(CategoryModel.ROOT_ID.toString())));
            categoryGroups.add(
                    new MemoryCategoryGroupEntity("2", "All", "https://unsplash.it/300?random",
                            root
                    )
            );

            started = true;
        }


        return categoryGroups;
    }

    @Override
    public List<? extends CategoryGroupModel> mainViewCategoriesGroups() {
        return loadAll();
    }

    @Override
    public void insert(CategoryGroupModel categoryGroupModel) {
        categoryGroups.add(categoryGroupModel);
    }

    @Override
    public void insertAll(List<CategoryGroupModel> categoryGroupModelList) {
        for (CategoryGroupModel category :
                categoryGroupModelList) {
            categoryGroups.add(category);
        }
    }

    @Override
    public void remove(String categoryGroupId) {
        for (Iterator<CategoryGroupModel> iterator = categoryGroups.iterator(); iterator.hasNext();) {
            CategoryGroupModel item = iterator.next();
            if (item.getStringId().equals(categoryGroupId)) {
                iterator.remove();
                return;
            }
        }
    }

    @Override
    public void removeAll() {
        categoryGroups.clear();
    }
}
