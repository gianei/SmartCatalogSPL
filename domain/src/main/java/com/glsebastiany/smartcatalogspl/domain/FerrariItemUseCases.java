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


import com.glsebastiany.smartcatalogspl.data.cars.CarItemModel;
import com.glsebastiany.smartcatalogspl.data.cars.CarItemsItemRepository;
import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.data.ItemRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

@Singleton
public class FerrariItemUseCases implements ItemUseCases {

    private ItemRepository itemRepository;

    @Inject
    public FerrariItemUseCases(CarItemsItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Override
    public Observable<ItemModel> allItems(){
        return itemRepository.getAll();
        //here we can filter or map or whatever
    }

    @Override
    public Observable<ItemModel> mainViewItems(){
        return itemRepository.getAll().filter(new Func1<ItemModel, Boolean>() {
            @Override
            public Boolean call(ItemModel s) {
                CarItemModel item =  (CarItemModel) s;
                return item.getCompany().equals("Ferrari");
            }
        });
        //here we can filter or map or whatever
    }
}
