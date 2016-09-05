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

package com.glsebastiany.smartcatalogspl.data.cars;


import android.os.Handler;

import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.data.ItemRepository;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class CarItemsItemRepository implements ItemRepository {

    @Inject //must have constructor so that it can be magically created
    public CarItemsItemRepository(){}

    @Override
    public Observable<ItemModel> getAll(){
        return Observable.create(new Observable.OnSubscribe<ItemModel>(){
            @Override
            public void call(final Subscriber<? super ItemModel> subscriber) {
                try {
                    Handler handler = new Handler();
                    int delay = 1000;
                    for (final ItemModel item :
                            fetchFromMemory()) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onNext(item);
                            }
                        }, delay);

                        delay += 500;
                    }

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            subscriber.onCompleted();
                        }
                    }, delay);
                } catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });

    }

    private List<ItemModel> fetchFromMemory() {
        List<ItemModel> items = new LinkedList<>();
        items.add(new CarItemModel("1", "C1", "F1", "Ferrari", 2016));
        items.add(new CarItemModel("3", "C2", "Gol", "Volkswagen", 2005));
        items.add(new CarItemModel("2", "C1", "F11", "Ferrari", 2015));
        items.add(new CarItemModel("4", "C3","Fusca", "Volkswagen", 1984));
        return items;
    }
}
