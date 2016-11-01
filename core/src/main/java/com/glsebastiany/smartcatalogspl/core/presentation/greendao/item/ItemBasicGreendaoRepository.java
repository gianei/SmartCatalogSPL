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

package com.glsebastiany.smartcatalogspl.core.presentation.greendao.item;

import android.content.Context;

import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.GreenDaoOpenHelper;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ItemBasicGreendaoRepository implements ItemBasicRepository {

    private final Context context;

    @Inject
    public ItemBasicGreendaoRepository(Context context){
        this.context = context;
    }

    @Override
    public List<? extends ItemBasicModel> loadAll() {
        return GreenDaoOpenHelper.daoSession(context).getItemBasicEntityDao().loadAll();
    }

    @Override
    public ItemBasicModel load(String itemId) {
        return GreenDaoOpenHelper.daoSession(context).getItemBasicEntityDao().load(Utils.parseLong(itemId));
    }

    @Override
    public List<? extends ItemBasicModel> query(String query) {
        String myQuery = query.trim();
        myQuery = myQuery.replaceAll("\\s+", " "); //only allow one whitespace between words
        myQuery = myQuery.replaceAll(" ", "%"); //insert wild card between words
        myQuery = myQuery.replaceAll("[^\\p{ASCII}]", "_"); //replace accents for any character
        ItemBasicEntityDao itemDao = GreenDaoOpenHelper.daoSession(context).getItemBasicEntityDao();

        return itemDao.queryBuilder()
                .whereOr(
                        ItemBasicEntityDao.Properties.Id.eq(myQuery),
                        ItemBasicEntityDao.Properties.Name.like("%" + myQuery + "%"))
                .list();
    }

    @Override
    public void insert(ItemBasicModel itemBasicModel) {
        GreenDaoOpenHelper.daoSession(context).getItemBasicEntityDao()
                .insert((ItemBasicEntity) itemBasicModel);
    }

    @Override
    public void insertAll(List<ItemBasicModel> itemBasicList) {
        ArrayList<ItemBasicEntity> myItems = new ArrayList<>(itemBasicList.size());
        for (int i = 0; i < itemBasicList.size(); i++)
            myItems.add((ItemBasicEntity) itemBasicList.get(i));

        GreenDaoOpenHelper.daoSession(context).getItemBasicEntityDao()
                .insertInTx(myItems);

    }

    @Override
    public void remove(String itemId) {
        GreenDaoOpenHelper.daoSession(context).getItemBasicEntityDao()
                .deleteByKey(Utils.parseLong(itemId));
    }

    @Override
    public void removeAll() {
        GreenDaoOpenHelper.daoSession(context).getItemBasicEntityDao()
                .deleteAll();
    }
}
