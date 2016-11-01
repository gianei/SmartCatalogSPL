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

package com.glsebastiany.smartcatalogspl.core.domain.item;


import com.glsebastiany.smartcatalogspl.core.data.item.ItemPromotedModel;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class ItemPromotedUseCases {

    @Inject
    ItemPromotedRepository itemPromotedRepository;

    @Inject
    public ItemPromotedUseCases(){}

    public Observable<ItemPromotedModel> getAll(){
        return Observable.create(new Observable.OnSubscribe<ItemPromotedModel>() {
            @Override
            public void call(Subscriber<? super ItemPromotedModel> subscriber) {
                for (ItemPromotedModel item:
                        itemPromotedRepository.loadAll()) {
                    subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }

    public Observable<ItemPromotedModel> getAllPromoted(){
        return Observable.create(new Observable.OnSubscribe<ItemPromotedModel>() {
            @Override
            public void call(Subscriber<? super ItemPromotedModel> subscriber) {

                for (ItemPromotedModel item:
                        itemPromotedRepository.loadAll()) {
                    if (item.getIsPromoted())
                        subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }

    public Observable<ItemPromotedModel> getAllPromotedOrSale(){
        return Observable.create(new Observable.OnSubscribe<ItemPromotedModel>() {
            @Override
            public void call(Subscriber<? super ItemPromotedModel> subscriber) {

                for (ItemPromotedModel item:
                        itemPromotedRepository.loadAll()) {
                    if (item.getIsPromoted() || item.getIsSale())
                        subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }

    public Observable<ItemPromotedModel> getAllNew(){
        return Observable.create(new Observable.OnSubscribe<ItemPromotedModel>() {
            @Override
            public void call(Subscriber<? super ItemPromotedModel> subscriber) {
                for (ItemPromotedModel item:
                        itemPromotedRepository.loadAll()) {

                    if (item.getIsNew())
                        subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }

    public Observable<ItemPromotedModel> getAllSale(){
        return Observable.create(new Observable.OnSubscribe<ItemPromotedModel>() {
            @Override
            public void call(Subscriber<? super ItemPromotedModel> subscriber) {
                for (ItemPromotedModel item:
                        itemPromotedRepository.loadAll()) {

                    if (item.getIsSale())
                        subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }

    public void insert(ItemPromotedModel itemPromotedModel){
        itemPromotedRepository.insert(itemPromotedModel);
    }

    public void insertAll(List<ItemPromotedModel> itemPromotedList){
        itemPromotedRepository.insertAll(itemPromotedList);
    }

    public void remove(String categoryId){
        itemPromotedRepository.remove(categoryId);
    }

    public void removeAll(){
        itemPromotedRepository.removeAll();
    }
}
