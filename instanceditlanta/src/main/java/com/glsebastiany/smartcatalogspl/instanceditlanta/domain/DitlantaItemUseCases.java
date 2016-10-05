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

package com.glsebastiany.smartcatalogspl.instanceditlanta.domain;


import android.content.Context;
import android.support.annotation.NonNull;

import com.glsebastiany.smartcatalogspl.core.Utils;
import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.GreenDaoOpenHelper;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.Category;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.Item;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.ItemDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class DitlantaItemUseCases implements ItemUseCases {

    private final Context context;

    @Inject
    CategoryUseCases categoryUseCases;

    @Inject
    public DitlantaItemUseCases(Context context){
        this.context = context;
    }

    @Override
    public Observable<ItemModel> getAll() {
        return Observable.create(new Observable.OnSubscribe<ItemModel>() {
            @Override
            public void call(Subscriber<? super ItemModel> subscriber) {
                ItemDao itemDao = GreenDaoOpenHelper.daoSession(context).getItemDao();
                for (ItemModel item :
                        itemDao.loadAll()) {
                    subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<ItemModel> mainViewItems() {
        return getAll();
    }

    @Override
    public Observable<ItemModel> allFromCategory(final String categoryId) {
        return Observable.create(new Observable.OnSubscribe<ItemModel>() {
            @Override
            public void call(Subscriber<? super ItemModel> subscriber) {
                List<Long> categoryModelsIds = getSubcategoriesIds(categoryId);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<Item> items = new ArrayList<>();
                for (Item item : GreenDaoOpenHelper.daoSession(context).getItemDao().loadAll()){
                    try {
                        if (categoryModelsIds.contains(item.getCategory().getId()))
                            items.add(item);
                    } catch (NullPointerException e){
                        continue;
                    }
                }

                switch (categoryId){
                    case DitlantaCategoryUseCases.ID_PROMOTION:
                        items = getAllPromoted();
                        break;
                    case DitlantaCategoryUseCases.ID_SALE:
                        items = getAllSale();
                        break;
                    case DitlantaCategoryUseCases.ID_NEW:
                        items = getAllNew();
                        break;
                    case DitlantaCategoryUseCases.ID_PROMOTION_AND_SALE:
                        items = getAllPromotedOrSale();
                        break;
                }

                orderByCategoryBasePrice(items);

                for (Item item:
                     items) {
                    subscriber.onNext(item);
                }

                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<ItemModel> find(final String itemId) {
        return Observable.create(new Observable.OnSubscribe<ItemModel>() {
            @Override
            public void call(Subscriber<? super ItemModel> subscriber) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Item item = null;
                try {
                    item = GreenDaoOpenHelper.daoSession(context).getItemDao().load(Utils.parseLong(itemId));
                } catch (NumberFormatException e){
                    subscriber.onError(e);
                }
                if (item != null) {
                    subscriber.onNext(item);
                    subscriber.onCompleted();
                } else
                    subscriber.onError(new RuntimeException("Item id " + itemId + " was not found!"));
            }
        });
    }

    public List<Item> getAllPromoted(){
        List<Item> items = GreenDaoOpenHelper.daoSession(context).getItemDao().loadAll();

        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = (Item)iterator.next(); // must be called before you can call i.remove()

            if (!item.getIsPromoted())
                iterator.remove();
        }

        return items;
    }

    public List<Item> getAllPromotedOrSale(){
        List<Item> items = GreenDaoOpenHelper.daoSession(context).getItemDao().loadAll();

        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = (Item)iterator.next(); // must be called before you can call i.remove()

            if (!item.getIsPromoted() &&!item.getIsSale())
                iterator.remove();
        }

        return items;
    }

    public List<Item> getAllNew(){
        List<Item> items = GreenDaoOpenHelper.daoSession(context).getItemDao().loadAll();

        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = (Item)iterator.next(); // must be called before you can call i.remove()

            if (!item.getIsNew())
                iterator.remove();
        }

        return items;
    }

    public List<Item> getAllSale(){
        List<Item> items = GreenDaoOpenHelper.daoSession(context).getItemDao().loadAll();

        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = (Item)iterator.next(); // must be called before you can call i.remove()

            if (!item.getIsSale())
                iterator.remove();
        }

        return items;
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

    private void orderByCategoryBasePrice(List<? extends Item> Items){
        if (Items == null)
            return;
        Map<Long, Integer> orderedIds = getOrderedIds();
        Collections.sort(Items, new CategoryBasePriceComparator<>(orderedIds));
    }

    private Map<Long, Integer> getOrderedIds() {
        List<Long> categories = getSubcategoriesIds(Category.ROOT_ID.toString());

        Map<Long,Integer> categoriesMap = new HashMap<>();
        int i = 0;
        for (Long id : categories)
            categoriesMap.put(id,i++);

        return categoriesMap;
    }

    private static class CategoryBasePriceComparator<T extends Item> implements Comparator<T> {

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

    /*public List<? extends Item> getQuery(String query){
        query = query.trim();
        query = query.replaceAll("\\s+", " "); //only allow one whitespace between words
        query = query.replaceAll(" ", "%"); //insert wild card between words
        query = query.replaceAll("[^\\p{ASCII}]", "_"); //replace accents for any character
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        ItemDao itemDao = daoSession.getItemDao();
        List<Item> list = itemDao.queryBuilder().whereOr(ItemDao.Properties.Id.eq(query), ItemDao.Properties.Name.like("%" + query + "%")).list();
        QueryBuilder.LOG_SQL = false;
        QueryBuilder.LOG_VALUES = false;
        return list;
    }*/

    public void removeAll(){
        GreenDaoOpenHelper.daoSession(context).getItemDao().deleteAll();
    }

    public void remove(long id) {
        GreenDaoOpenHelper.daoSession(context).getItemDao().deleteByKey(id);
    }

    public void insert(List itemList){
        ArrayList<Item> myItems = new ArrayList<>(itemList.size());
        for (int i = 0; i < itemList.size(); i++)
            myItems.add((Item) itemList.get(i));
        GreenDaoOpenHelper.daoSession(context).getItemDao().insertInTx(myItems);
    }

    public void insert(Object o) {
        GreenDaoOpenHelper.daoSession(context).insert(o);
    }

}
