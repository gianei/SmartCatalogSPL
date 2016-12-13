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

import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class ItemBasicUseCases {

    @Inject
    ItemBasicRepository itemBasicRepository;

    @Inject
    public ItemBasicUseCases(){}

    public Observable<ItemBasicModel> getAll() {
        return Observable.create(
                subscriber -> {
                    for (ItemBasicModel item :
                            itemBasicRepository.loadAll()) {
                        subscriber.onNext(item);
                    }

                    subscriber.onComplete();
                }
        );
    }


    public Observable<ItemBasicModel> find(final String itemId) {
        return Observable.create(new ObservableOnSubscribe<ItemBasicModel>() {
            @Override
            public void subscribe(ObservableEmitter<ItemBasicModel> subscriber) throws Exception {
                ItemBasicModel item = itemBasicRepository.load(itemId);

                if (item != null) {
                    subscriber.onNext(item);
                    subscriber.onComplete();
                } else
                    subscriber.onError(new RuntimeException("Item id " + itemId + " was not found!"));
            }
        });
    }

    public Observable<ItemBasicModel> query(final String query) {
        return Observable.create(new ObservableOnSubscribe<ItemBasicModel>() {
            @Override
            public void subscribe(ObservableEmitter<ItemBasicModel> subscriber) throws Exception {
                List<? extends ItemBasicModel> list = itemBasicRepository.query(query);

                for (ItemBasicModel item : list) {
                    subscriber.onNext(item);
                }

                subscriber.onComplete();
            }
        });
    }



    public void insert(ItemBasicModel itemBasicModel){
        itemBasicRepository.insert(itemBasicModel);
    }

    public void insertAll(List<ItemBasicModel> itemBasicList){
        itemBasicRepository.insertAll(itemBasicList);
    }

    public void remove(String categoryId){
        itemBasicRepository.remove(categoryId);
    }

    public void removeAll(){
        itemBasicRepository.removeAll();
    }

}
