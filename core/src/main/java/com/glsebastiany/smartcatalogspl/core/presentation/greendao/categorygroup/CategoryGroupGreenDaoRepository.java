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

package com.glsebastiany.smartcatalogspl.core.presentation.greendao.categorygroup;


import android.content.Context;

import com.glsebastiany.smartcatalogspl.core.data.categorygroup.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupRepository;
import com.glsebastiany.smartcatalogspl.core.presentation.greendao.GreenDaoOpenHelper;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CategoryGroupGreendaoRepository implements CategoryGroupRepository {

    private final Context context;

    @Inject
    public CategoryGroupGreendaoRepository(Context context){
        this.context = context;
    }

    @Override
    public List<? extends CategoryGroupModel> loadAll() {
        return GreenDaoOpenHelper.daoSession(context).getCategoryGroupEntityDao()
                .loadAll();
    }

    @Override
    public List<? extends CategoryGroupModel> mainViewCategoriesGroups() {
        CategoryGroupEntityDao categoryGroupDao = GreenDaoOpenHelper.daoSession(context).getCategoryGroupEntityDao();

        return categoryGroupDao.queryBuilder()
                .orderAsc(CategoryGroupEntityDao.Properties.Order)
                .list();
    }

    @Override
    public void insert(CategoryGroupModel categoryGroupModel) {
        GreenDaoOpenHelper.daoSession(context).getCategoryGroupEntityDao()
                .insert((CategoryGroupEntity) categoryGroupModel);
    }

    @Override
    public void insertAll(List<CategoryGroupModel> categoryGroupModelList) {
        ArrayList<CategoryGroupEntity> myCategoryGroups = new ArrayList<>(categoryGroupModelList.size());
        for (int i = 0; i < categoryGroupModelList.size(); i++)
            myCategoryGroups.add((CategoryGroupEntity) categoryGroupModelList.get(i));

        GreenDaoOpenHelper.daoSession(context).getCategoryGroupEntityDao()
                .insertInTx(myCategoryGroups);
    }

    @Override
    public void remove(String categoryGroupId) {
        GreenDaoOpenHelper.daoSession(context).getCategoryGroupEntityDao()
                .deleteByKey(Utils.parseLong(categoryGroupId));

    }

    @Override
    public void removeAll() {
        GreenDaoOpenHelper.daoSession(context).getCategoryGroupEntityDao()
                .deleteAll();
    }

}
