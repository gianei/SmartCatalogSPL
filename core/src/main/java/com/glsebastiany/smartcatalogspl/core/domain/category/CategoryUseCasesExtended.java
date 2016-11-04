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

package com.glsebastiany.smartcatalogspl.core.domain.category;


import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemPromotedModel;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemPromotedUseCases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

public class CategoryUseCasesExtended extends CategoryUseCases{

    ItemPromotedUseCases itemPromotedUseCases;

    @Override
    public Observable<ItemBasicModel> allItemsFromCategory(final String categoryId) {

        return Observable.create(new Observable.OnSubscribe<ItemBasicModel>() {
            @Override
            public void call(Subscriber<? super ItemBasicModel> subscriber) {

                List<ItemPromotedModel> promotedItems = null;

                switch (categoryId) {
                    case CategoryRepository.ID_PROMOTION:
                        promotedItems = itemPromotedUseCases.getAllPromoted().toList().toBlocking().single();
                        break;
                    case CategoryRepository.ID_SALE:
                        promotedItems = itemPromotedUseCases.getAllSale().toList().toBlocking().single();
                        break;
                    case CategoryRepository.ID_NEW:
                        promotedItems = itemPromotedUseCases.getAllNew().toList().toBlocking().single();
                        break;
                    case CategoryRepository.ID_PROMOTION_AND_SALE:
                        promotedItems = itemPromotedUseCases.getAllPromotedOrSale().toList().toBlocking().single();
                        break;
                }

                if (promotedItems != null){
                    orderByCategoryBasePricePromoted(promotedItems);
                    for (ItemPromotedModel item:
                            promotedItems) {
                        subscriber.onNext(item.getItemBasicEntity());
                    }

                    subscriber.onCompleted();
                    return;
                }



                List<String> categoryModelsIds = getSubcategoriesIds(categoryId);

                List<ItemBasicModel> items = new ArrayList<>();
                for (ItemBasicModel item : itemBasicRepository.loadAll()){
                    try {
                        if (categoryModelsIds.contains(item.getCategory().getStringId()))
                            items.add(item);
                    } catch (NullPointerException e){
                        continue;
                    }
                }

                orderByCategoryBasePrice(items);

                for (ItemBasicModel item:
                        items) {
                    subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }


    public void orderByCategoryBasePricePromoted(List<? extends ItemPromotedModel> Items){
        if (Items == null)
            return;
        Map<String, Integer> orderedIds = getOrderedIds();
        Collections.sort(Items, new CategoryBasePriceComparatorPromoted<>(orderedIds));
        return;
    }

    private static class CategoryBasePriceComparatorPromoted<T extends ItemPromotedModel> implements Comparator<T> {

        private final Map<String,Integer> categoriesPositions;

        CategoryBasePriceComparatorPromoted(Map<String, Integer> categoriesPositions){
            this.categoriesPositions = categoriesPositions;
        }

        @Override
        public int compare(T lhs, T rhs) {
            if (!categoriesPositions.containsKey(lhs.getItemBasicEntity().getCategoryStringId()))
                return 0;
            if (!categoriesPositions.containsKey(rhs.getItemBasicEntity().getCategoryStringId()))
                return 0;
            int firstComparator = categoriesPositions.get(lhs.getItemBasicEntity().getCategoryStringId()).
                    compareTo(categoriesPositions.get(rhs.getItemBasicEntity().getCategoryStringId()));

            if (firstComparator != 0)
                return firstComparator;
            else
                return Float.compare(lhs.getItemBasicEntity().getPrice(), rhs.getItemBasicEntity().getPrice());

        }

    }


}
