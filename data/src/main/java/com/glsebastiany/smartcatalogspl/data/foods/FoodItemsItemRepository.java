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

package com.glsebastiany.smartcatalogspl.data.foods;


import android.os.Handler;

import com.glsebastiany.smartcatalogspl.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.data.ItemRepository;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class FoodItemsItemRepository implements ItemRepository {

    @Inject //must have constructor so that it can be magically created
    public FoodItemsItemRepository(){}

    @Override
    public Observable<ItemModel> getAll(){
        return Observable.create(new Observable.OnSubscribe<ItemModel>(){
            @Override
            public void call(final Subscriber<? super ItemModel> subscriber) {
                try {
                    final List<ItemModel> userStr = getFoods();

                    for (ItemModel item :
                            userStr) {
                        subscriber.onNext(item);
                    }

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            subscriber.onCompleted();
                        }
                    }, 2000);
                } catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    private List<ItemModel> getFoods() {
        List<ItemModel> items = new LinkedList<>();
        items.add(new FoodItemModel("Pasta"));
        items.add(new FoodItemModel("Toasts"));
        items.add(new FoodItemModel("Eggs"));
        items.add(new FoodItemModel("Beer"));
        items.add(new FoodItemModel("Wine"));
        items.add(new FoodItemModel("AppleJuice"));
        items.add(new FoodItemModel("Brigadeiro"));
        items.add(new FoodItemModel("Fudge"));
        items.add(new FoodItemModel("JellyBean"));
        items.add(new FoodItemModel("KitKat"));
        items.add(new FoodItemModel("Marshmallow"));
        items.add(new FoodItemModel("Nougat"));
        return items;
    }
}
