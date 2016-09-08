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

import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.GreenDaoOpenHelper;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.Item;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.ItemDao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class DitlantaItemUseCases implements ItemUseCases {

    private final Context context;

    @Inject
    public DitlantaItemUseCases(Context context){
        this.context = context;
    }

    @Override
    public Observable<ItemModel> getAll() {
        return ObservableHelper.createThreaded(new Observable.OnSubscribe<ItemModel>() {
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
    public Observable<ItemModel> allFromCategory(String categoryId) {
        return getAll();
    }

    /*public List<? extends BaseItem> getQuery(String query){
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
