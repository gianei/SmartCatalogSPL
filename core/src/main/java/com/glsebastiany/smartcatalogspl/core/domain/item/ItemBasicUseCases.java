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

import android.support.annotation.NonNull;

import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemPromotedModel;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.category.CategoryGreendaoRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class ItemBasicUseCases {

    @Inject
    CategoryUseCases categoryUseCases;

    @Inject
    ItemBasicRepository itemBasicRepository;

    @Inject
    ItemPromotedUseCases itemPromotedUseCases;

    @Inject
    public ItemBasicUseCases(){}

    public Observable<ItemBasicModel> getAll() {
        return Observable.create(new Observable.OnSubscribe<ItemBasicModel>() {
            @Override
            public void call(Subscriber<? super ItemBasicModel> subscriber) {
                for (ItemBasicModel item :
                        itemBasicRepository.loadAll()) {
                    subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }


    public Observable<ItemBasicModel> find(final String itemId) {
        return Observable.create(new Observable.OnSubscribe<ItemBasicModel>() {
            @Override
            public void call(Subscriber<? super ItemBasicModel> subscriber) {
                ItemBasicModel item = itemBasicRepository.load(itemId);

                if (item != null) {
                    subscriber.onNext(item);
                    subscriber.onCompleted();
                } else
                    subscriber.onError(new RuntimeException("Item id " + itemId + " was not found!"));
            }
        });
    }

    public Observable<ItemBasicModel> query(final String query) {
        return Observable.create(new Observable.OnSubscribe<ItemBasicModel>() {
            @Override
            public void call(Subscriber<? super ItemBasicModel> subscriber) {
                List<? extends ItemBasicModel> list = itemBasicRepository.query(query);

                for (ItemBasicModel item : list) {
                    subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }

    public Observable<ItemBasicModel> allFromCategory(final String categoryId) {
        return Observable.create(new Observable.OnSubscribe<ItemBasicModel>() {
            @Override
            public void call(Subscriber<? super ItemBasicModel> subscriber) {
                List<Long> categoryModelsIds = getSubcategoriesIds(categoryId);

                List<ItemBasicModel> items = new ArrayList<>();
                for (ItemBasicModel item : itemBasicRepository.loadAll()){
                    try {
                        if (categoryModelsIds.contains(item.getCategory().getId()))
                            items.add(item);
                    } catch (NullPointerException e){
                        continue;
                    }
                }


                //TODO
                /*switch (categoryId){
                    case CategoryGreendaoRepository.ID_PROMOTION:
                        itemPromotedUseCases.getAllPromoted().map(new Func1<ItemPromotedModel, ItemBasicModel>() {
                            @Override
                            public ItemBasicModel call(ItemPromotedModel itemPromotedModel) {
                                return itemPromotedModel.get;
                            }
                        });
                        break;
                    case CategoryGreendaoRepository.ID_SALE:
                        items = getAllSale();
                        break;
                    case CategoryGreendaoRepository.ID_NEW:
                        items = getAllNew();
                        break;
                    case CategoryGreendaoRepository.ID_PROMOTION_AND_SALE:
                        items = getAllPromotedOrSale();
                        break;
                }*/

                                                                                          orderByCategoryBasePrice(items);

                for (ItemBasicModel item:
                        items) {
                    subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }


    @NonNull
    private List<Long> getSubcategoriesIds(String categoryId) {
        CategoryModel category = categoryUseCases.findCategory(categoryId).toBlocking().single();
        List<CategoryModel> categoryModels = categoryUseCases.getAllChildren(category).toList().toBlocking().single();

        List<Long> categoryModelsIds = new ArrayList<>(categoryModels.size());
        for (CategoryModel categoryModel :
                categoryModels) {
            categoryModelsIds.add(Utils.parseLong(categoryModel.getStringId()));
        }
        return categoryModelsIds;
    }

    private void orderByCategoryBasePrice(List<? extends ItemBasicModel> Items){
        if (Items == null)
            return;
        Map<Long, Integer> orderedIds = getOrderedIds();
        Collections.sort(Items, new CategoryBasePriceComparator<>(orderedIds));
    }

    private Map<Long, Integer> getOrderedIds() {
        List<Long> categories = getSubcategoriesIds(CategoryModel.ROOT_ID.toString());

        Map<Long,Integer> categoriesMap = new HashMap<>();
        int i = 0;
        for (Long id : categories)
            categoriesMap.put(id,i++);

        return categoriesMap;
    }

    private static class CategoryBasePriceComparator<T extends ItemBasicModel> implements Comparator<T> {

        private final Map<Long,Integer> categoriesPositions;

        CategoryBasePriceComparator(Map<Long, Integer> categoriesPositions){
            this.categoriesPositions = categoriesPositions;
        }

        @Override
        public int compare(T lhs, T rhs) {
            if (!categoriesPositions.containsKey(lhs.getCategoryId()))
                return 0;
            if (!categoriesPositions.containsKey(rhs.getCategoryId()))
                return 0;
            int firstComparator = categoriesPositions.get(lhs.getCategoryId()).
                    compareTo(categoriesPositions.get(rhs.getCategoryId()));

            if (firstComparator != 0)
                return firstComparator;
            else
                return Float.compare(lhs.getPrice(), rhs.getPrice());

        }

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
