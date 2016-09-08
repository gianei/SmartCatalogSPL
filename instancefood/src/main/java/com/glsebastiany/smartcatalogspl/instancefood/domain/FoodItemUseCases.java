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

package com.glsebastiany.smartcatalogspl.instancefood.domain;


import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.instancefood.data.FoodItemModel;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

@Singleton
public class FoodItemUseCases implements ItemUseCases {

    List<ItemModel> items = new LinkedList<>();

    @Inject //must have constructor so that it can be magically created
    public FoodItemUseCases(){
        items.add(new FoodItemModel("Tagliateri", "Pasta"));
        items.add(new FoodItemModel("Toasts", "Breakfest"));
        items.add(new FoodItemModel("Eggs", "Breakfest"));
        items.add(new FoodItemModel("Beer", "Alcoholic"));
        items.add(new FoodItemModel("Wine", "Alcoholic"));
        items.add(new FoodItemModel("AppleJuice", "Juice"));
        items.add(new FoodItemModel("Brigadeiro", "Sweet"));
        items.add(new FoodItemModel("Fudge", "Sweet"));
        items.add(new FoodItemModel("JellyBean", "Android"));
        items.add(new FoodItemModel("KitKat", "Android"));
        items.add(new FoodItemModel("Marshmallow", "Android"));
        items.add(new FoodItemModel("Nougat", "Android"));
    }

    @Override
    public Observable<ItemModel> getAll(){
        return ObservableHelper.createThreaded(new Observable.OnSubscribe<ItemModel>(){
            @Override
            public void call(final Subscriber<? super ItemModel> subscriber) {
                try {
                    for (ItemModel item :
                            items) {
                        Thread.sleep(100);
                        subscriber.onNext(item);
                    }

                    subscriber.onCompleted();

                } catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<ItemModel> mainViewItems() {
        return getAll();
    }

    @Override
    public Observable<ItemModel> allFromCategory(final String categoryId) {
        return getAll().filter(new Func1<ItemModel, Boolean>() {
            @Override
            public Boolean call(ItemModel s) {
                FoodItemModel item =  (FoodItemModel) s;
                return item.getCategoryId().equals(categoryId);
            }
        });
    }
}
