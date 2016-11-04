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

import com.glsebastiany.smartcatalogspl.core.data.item.ItemExtendedModel;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemExtendedRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.GreenDaoOpenHelper;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ItemExtendedGreendaoRepository implements ItemExtendedRepository {

    private final Context context;

    @Inject
    public ItemExtendedGreendaoRepository(Context context){
        this.context = context;
    }

    @Override
    public List<? extends ItemExtendedModel> loadAll() {
        return GreenDaoOpenHelper.daoSession(context).getItemExtendedEntityDao().loadAll();
    }

    @Override
    public ItemExtendedModel load(String itemId) {
        return GreenDaoOpenHelper.daoSession(context).getItemExtendedEntityDao().load(Utils.parseLong(itemId));
    }

    @Override
    public void insert(ItemExtendedModel itemExtendedModel) {
        GreenDaoOpenHelper.daoSession(context).getItemExtendedEntityDao()
                .insert((ItemExtendedEntity) itemExtendedModel);
    }

    @Override
    public void insertAll(List<ItemExtendedModel> itemPromotedList) {
        ArrayList<ItemExtendedEntity> myItems = new ArrayList<>(itemPromotedList.size());
        for (int i = 0; i < itemPromotedList.size(); i++)
            myItems.add((ItemExtendedEntity) itemPromotedList.get(i));

        GreenDaoOpenHelper.daoSession(context).getItemExtendedEntityDao()
                .insertInTx(myItems);
    }

    @Override
    public void remove(String itemId) {
        GreenDaoOpenHelper.daoSession(context).getItemExtendedEntityDao()
                .deleteByKey(Utils.parseLong(itemId));
    }

    @Override
    public void removeAll() {
        GreenDaoOpenHelper.daoSession(context).getItemExtendedEntityDao()
                .deleteAll();

    }

}
