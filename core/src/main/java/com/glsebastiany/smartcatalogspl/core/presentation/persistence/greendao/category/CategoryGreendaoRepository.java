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

package com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.category;


import android.content.Context;

import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryRepository;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryUseCasesExtended;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.GreenDaoOpenHelper;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

public class CategoryGreendaoRepository implements CategoryRepository{

    private final Context context;

    @Inject
    public CategoryGreendaoRepository(Context context){
        this.context = context;
    }

    @Override
    public List<? extends CategoryModel> loadAll() {

        List<CategoryEntity> categories =
                GreenDaoOpenHelper.daoSession(context).getCategoryEntityDao().loadAll();


        categories.add(new CategoryEntity(Utils.parseLong(CategoryUseCasesExtended.ID_PROMOTION), CategoryModel.ROOT_ID, "Promoções"));
        categories.add(new CategoryEntity(Utils.parseLong(CategoryUseCasesExtended.ID_SALE), CategoryModel.ROOT_ID, "Liquidação"));
        categories.add(new CategoryEntity(Utils.parseLong(CategoryUseCasesExtended.ID_NEW), CategoryModel.ROOT_ID, "Novidades"));
        categories.add(new CategoryEntity(Utils.parseLong(CategoryUseCasesExtended.ID_PROMOTION_AND_SALE), CategoryModel.ROOT_ID, "Promoções e Liquidação"));
        categories.add(new CategoryEntity(Utils.parseLong(CategoryUseCasesExtended.ID_OTHER), CategoryModel.ROOT_ID, "Outro"));

        return categories;
    }

    @Override
    public CategoryModel load(String categoryId) {
        CategoryModel category = GreenDaoOpenHelper.daoSession(context).getCategoryEntityDao().load(Utils.parseLong(categoryId));
        if (category == null){
            category = getSpecialCategory(categoryId);
        }

        return category;
    }

    private CategoryModel getSpecialCategory(String categoryId) {
        CategoryModel category;
        switch (categoryId){
            case CategoryUseCasesExtended.ID_PROMOTION:
                category = new CategoryEntity(Utils.parseLong(CategoryUseCasesExtended.ID_PROMOTION), CategoryModel.ROOT_ID, "Promoções");
                break;
            case CategoryUseCasesExtended.ID_SALE:
                category = new CategoryEntity(Utils.parseLong(CategoryUseCasesExtended.ID_SALE), CategoryModel.ROOT_ID, "Liquidação");
                break;
            case CategoryUseCasesExtended.ID_NEW:
                category = new CategoryEntity(Utils.parseLong(CategoryUseCasesExtended.ID_NEW), CategoryModel.ROOT_ID, "Novidades");
                break;
            case CategoryUseCasesExtended.ID_PROMOTION_AND_SALE:
                category = new CategoryEntity(Utils.parseLong(CategoryUseCasesExtended.ID_PROMOTION_AND_SALE), CategoryModel.ROOT_ID, "Promoções e Liquidação");
                break;
            default:
                category = new CategoryEntity(Utils.parseLong(CategoryUseCasesExtended.ID_OTHER), CategoryModel.ROOT_ID, "Outro");
        }

        return category;
    }

    @Override
    public List<? extends CategoryModel> load(List<String> categoriesId) {
        if(categoriesId.size() > 0) {
            List<CategoryModel> categories = new LinkedList<>();

            for (int i = 0; i < categoriesId.size(); i++) {
                categories.add(load(categoriesId.get(i)));
            }

            return categories;

        } else {
            return new LinkedList<>();
        }
    }

    @Override
    public void insert(CategoryModel categoryModel) {
        GreenDaoOpenHelper.daoSession(context).getCategoryEntityDao()
                .insert((CategoryEntity)categoryModel);
    }

    @Override
    public void insertAll(List<CategoryModel> categoryList) {
        ArrayList<CategoryEntity> myCategories = new ArrayList<>(categoryList.size());
        for (int i = 0; i < categoryList.size(); i++)
            myCategories.add((CategoryEntity) categoryList.get(i));

        GreenDaoOpenHelper.daoSession(context).getCategoryEntityDao()
                .insertInTx(myCategories);
    }

    @Override
    public void remove(String categoryId) {
        GreenDaoOpenHelper.daoSession(context).getCategoryEntityDao()
                .deleteByKey(Utils.parseLong(categoryId));
    }

    @Override
    public void removeAll() {
        GreenDaoOpenHelper.daoSession(context).getCategoryEntityDao()
                .deleteAll();
    }
}
