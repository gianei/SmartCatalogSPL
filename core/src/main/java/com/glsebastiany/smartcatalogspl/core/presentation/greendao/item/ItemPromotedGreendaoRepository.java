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

import com.glsebastiany.smartcatalogspl.core.data.item.ItemPromotedModel;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemPromotedRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.GreenDaoOpenHelper;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ItemPromotedGreendaoRepository implements ItemPromotedRepository {

    private final Context context;

    @Inject
    public ItemPromotedGreendaoRepository(Context context){
        this.context = context;
    }

    @Override
    public List<? extends ItemPromotedModel> loadAll() {
        return GreenDaoOpenHelper.daoSession(context).getItemPromotedEntityDao().loadAll();
    }

    @Override
    public ItemPromotedModel load(String itemId) {
        return GreenDaoOpenHelper.daoSession(context).getItemPromotedEntityDao().load(Utils.parseLong(itemId));
    }

    @Override
    public void insert(ItemPromotedModel itemPromotedModel) {
        GreenDaoOpenHelper.daoSession(context).getItemPromotedEntityDao()
                .insert((ItemPromotedEntity) itemPromotedModel);
    }

    @Override
    public void insertAll(List<ItemPromotedModel> itemPromotedList) {
        ArrayList<ItemPromotedEntity> myItems = new ArrayList<>(itemPromotedList.size());
        for (int i = 0; i < itemPromotedList.size(); i++)
            myItems.add((ItemPromotedEntity) itemPromotedList.get(i));

        GreenDaoOpenHelper.daoSession(context).getItemPromotedEntityDao()
                .insertInTx(myItems);
    }

    @Override
    public void remove(String itemId) {
        GreenDaoOpenHelper.daoSession(context).getItemPromotedEntityDao()
                .deleteByKey(Utils.parseLong(itemId));
    }

    @Override
    public void removeAll() {
        GreenDaoOpenHelper.daoSession(context).getItemPromotedEntityDao()
                .deleteAll();

    }

}
