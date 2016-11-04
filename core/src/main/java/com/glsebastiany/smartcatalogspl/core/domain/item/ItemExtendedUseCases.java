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


import com.glsebastiany.smartcatalogspl.core.data.item.ItemExtendedModel;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class ItemExtendedUseCases {

    @Inject
    ItemExtendedRepository itemExtendedRepository;

    @Inject
    public ItemExtendedUseCases(){}

    public Observable<ItemExtendedModel> load(final String itemId){
        return Observable.create(new Observable.OnSubscribe<ItemExtendedModel>() {
            @Override
            public void call(Subscriber<? super ItemExtendedModel> subscriber) {
                ItemExtendedModel itemExtendedModel = itemExtendedRepository.load(itemId);

                if (itemExtendedModel == null)
                    subscriber.onError(new Throwable("Promoted item " + itemId + " was not found."));
                else
                    subscriber.onNext(itemExtendedModel);

                subscriber.onCompleted();
            }
        });
    }

    public Observable<ItemExtendedModel> getAll(){
        return Observable.create(new Observable.OnSubscribe<ItemExtendedModel>() {
            @Override
            public void call(Subscriber<? super ItemExtendedModel> subscriber) {
                for (ItemExtendedModel item:
                        itemExtendedRepository.loadAll()) {
                    subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }

    public Observable<ItemExtendedModel> getAllPromoted(){
        return Observable.create(new Observable.OnSubscribe<ItemExtendedModel>() {
            @Override
            public void call(Subscriber<? super ItemExtendedModel> subscriber) {

                for (ItemExtendedModel item:
                        itemExtendedRepository.loadAll()) {
                    if (item.getIsPromoted())
                        subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }

    public Observable<ItemExtendedModel> getAllPromotedOrSale(){
        return Observable.create(new Observable.OnSubscribe<ItemExtendedModel>() {
            @Override
            public void call(Subscriber<? super ItemExtendedModel> subscriber) {

                for (ItemExtendedModel item:
                        itemExtendedRepository.loadAll()) {
                    if (item.getIsPromoted() || item.getIsSale())
                        subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }

    public Observable<ItemExtendedModel> getAllNew(){
        return Observable.create(new Observable.OnSubscribe<ItemExtendedModel>() {
            @Override
            public void call(Subscriber<? super ItemExtendedModel> subscriber) {
                for (ItemExtendedModel item:
                        itemExtendedRepository.loadAll()) {

                    if (item.getIsNew())
                        subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }

    public Observable<ItemExtendedModel> getAllSale(){
        return Observable.create(new Observable.OnSubscribe<ItemExtendedModel>() {
            @Override
            public void call(Subscriber<? super ItemExtendedModel> subscriber) {
                for (ItemExtendedModel item:
                        itemExtendedRepository.loadAll()) {

                    if (item.getIsSale())
                        subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }

    public void insert(ItemExtendedModel itemExtendedModel){
        itemExtendedRepository.insert(itemExtendedModel);
    }

    public void insertAll(List<ItemExtendedModel> itemPromotedList){
        itemExtendedRepository.insertAll(itemPromotedList);
    }

    public void remove(String categoryId){
        itemExtendedRepository.remove(categoryId);
    }

    public void removeAll(){
        itemExtendedRepository.removeAll();
    }
}
