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

package com.glsebastiany.smartcatalogspl.instancefood.memory.item;


import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicRepository;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.splash.SplashScreenBase;
import com.glsebastiany.smartcatalogspl.instancefood.memory.item.MemoryItemEntity;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.di.components.ApplicationComponent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

@Singleton
public class ItemBasicMemoryRepository implements ItemBasicRepository {

    private boolean started = false;

    List<ItemBasicModel> items = new LinkedList<>();

    @Inject
    public ItemBasicMemoryRepository(){

    }

    @Override
    public List<? extends ItemBasicModel> loadAll() {
        if (started ==false){
            CategoryRepository categoryRepository = Singletons.getInstance().categoryRepository;

            items.add(new MemoryItemEntity("Tagliateri", categoryRepository.load("Pasta"), "Tagliateri", 8.88f));
            items.add(new MemoryItemEntity("Toasts", categoryRepository.load("Breakfest"), "Toasts", 8.88f));
            items.add(new MemoryItemEntity("Eggs", categoryRepository.load("Breakfest"), "Eggs", 8.88f));
            items.add(new MemoryItemEntity("Beer", categoryRepository.load("Alcoholic"), "Beer", 8.88f));
            items.add(new MemoryItemEntity("Wine", categoryRepository.load("Alcoholic"), "Wine", 8.88f));
            items.add(new MemoryItemEntity("AppleJuice", categoryRepository.load("Juice"), "AppleJuice", 8.88f));
            items.add(new MemoryItemEntity("Brigadeiro", categoryRepository.load("Sweet"), "Brigadeiro", 8.88f));
            items.add(new MemoryItemEntity("Fudge", categoryRepository.load("Sweet"), "Fudge", 8.88f));
            items.add(new MemoryItemEntity("JellyBean", categoryRepository.load("Android"), "JellyBean", 8.88f));
            items.add(new MemoryItemEntity("KitKat", categoryRepository.load("Android"), "KitKat", 8.88f));
            items.add(new MemoryItemEntity("Marshmallow", categoryRepository.load("Android"), "Marshmallow", 8.88f));
            items.add(new MemoryItemEntity("Nougat", categoryRepository.load("Android"), "Nougat", 8.88f));

            started = true;
        }

        return items;
    }

    @Override
    public ItemBasicModel load(String itemId) {
        for (ItemBasicModel item :
                items) {
            if (item.getStringId().equals(itemId))
                return item;
        }

        return null;
    }

    @Override
    public List<? extends ItemBasicModel> query(String query) {
        List<ItemBasicModel> returnItems = new LinkedList<>();
        for (ItemBasicModel item :
                items) {
            if (item.getStringId().contains(query))
                returnItems.add(item);
        }

        return returnItems;
    }

    @Override
    public void insert(ItemBasicModel itemBasicModel) {
        items.add(itemBasicModel);
    }

    @Override
    public void insertAll(List<ItemBasicModel> itemBasicList) {
        for (ItemBasicModel item :
                itemBasicList) {
            items.add(item);
        }
    }

    @Override
    public void remove(String itemId) {
        for (Iterator<ItemBasicModel> iterator = items.iterator(); iterator.hasNext();) {
            ItemBasicModel item = iterator.next();
            if (item.getStringId().equals(itemId)) {
                iterator.remove();
                return;
            }
        }
    }

    @Override
    public void removeAll() {
        items.clear();
    }
}
