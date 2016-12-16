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

package com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.item;

import android.content.Context;

import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.GreenDaoOpenHelper;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.Permutations;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
        query = Utils.removeAccents(query).trim();
        query = query.replaceAll("\\s+", " "); //only allow one whitespace between words
        String splitQuery[] = query.split(" "); //split to generate other or wheres
        query = query.replaceAll(" ", "%"); //insert wild card between words

        ItemBasicEntityDao itemDao = GreenDaoOpenHelper.daoSession(context).getItemBasicEntityDao();
        Set<ItemBasicModel> returnList = new LinkedHashSet<>();

        //first search id
        returnList.addAll(
                searchId(query, itemDao)
        );

        //second list strings
        if (splitQuery.length == 1) { // only one word
            returnList.addAll( searchString(query, itemDao));

        } else if ( splitQuery.length <= 5) { // search for all permutations
            Permutations<String> queryPermutations = new Permutations<>(splitQuery);
            while (queryPermutations.hasNext()) {
                String queryPermutation[] = queryPermutations.next();
                returnList.addAll(
                        searchString(Utils.stringJoin(Arrays.asList(queryPermutation), "%"), itemDao)
                );
            }

        } else { // too much permutations, search only one order
            returnList.addAll(searchString(query, itemDao));
        }

        return new ArrayList<>(returnList);

    }

    private List<ItemBasicEntity> searchId(String query, ItemBasicEntityDao itemDao) {
        return itemDao.queryBuilder()
            .where(ItemBasicEntityDao.Properties.Id.eq(query))
            .list();
    }

    private List<ItemBasicEntity> searchString(String query, ItemBasicEntityDao itemDao) {
        return itemDao.queryBuilder()
                .where(
                        ItemBasicEntityDao.Properties.AsciiName.like("%" + query + "%"))
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
