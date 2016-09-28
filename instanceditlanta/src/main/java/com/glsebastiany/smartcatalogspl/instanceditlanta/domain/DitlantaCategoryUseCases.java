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

import com.glsebastiany.smartcatalogspl.core.Utils;
import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.GreenDaoOpenHelper;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.Category;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.CategoryDao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import de.greenrobot.dao.DaoException;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class DitlantaCategoryUseCases implements CategoryUseCases {

    public static final String ID_PROMOTION = "-1";
    public static final String ID_SALE = "-2";
    public static final String ID_NEW = "-3";
    public static final String ID_PROMOTION_AND_SALE = "-4";
    public static final String ID_OTHER = "-99";
    private final Context context;

    @Inject
    public DitlantaCategoryUseCases(Context context){
        this.context = context;
    }

    @Override
    public Observable<CategoryModel>  getAll() {
        return Observable.create(new Observable.OnSubscribe<CategoryModel>() {
            @Override
            public void call(Subscriber<? super CategoryModel> subscriber) {

                subscriber.onNext(new Category(Utils.parseLong(ID_PROMOTION), Category.ROOT_ID, "Promoções"));
                subscriber.onNext(new Category(Utils.parseLong(ID_SALE), Category.ROOT_ID, "Liquidação"));
                subscriber.onNext(new Category(Utils.parseLong(ID_NEW), Category.ROOT_ID, "Novidades"));
                subscriber.onNext(new Category(Utils.parseLong(ID_PROMOTION_AND_SALE), Category.ROOT_ID, "Promoções e Liquidação"));
                subscriber.onNext(new Category(Utils.parseLong(ID_OTHER), Category.ROOT_ID, "Outro"));

                CategoryDao categoryDao = GreenDaoOpenHelper.daoSession(context).getCategoryDao();
                for (CategoryModel categoryModel :
                        categoryDao.loadAll()) {
                    subscriber.onNext(categoryModel);
                }

                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<CategoryModel> getDirectChildren(CategoryModel categoryModel) {
        return getAll();
    }

    @Override
    public Observable<CategoryModel> getAllChildren(final CategoryModel category) {
        return Observable.create(new Observable.OnSubscribe<CategoryModel>() {
            @Override
            public void call(Subscriber<? super CategoryModel> subscriber) {
                LinkedList<CategoryModel> categories = new LinkedList<>();
                fillSubCategoriesId(categories, (Category) category);

                for (CategoryModel categoryModel : categories) {
                    subscriber.onNext(categoryModel);
                }

                subscriber.onCompleted();
            }
        });
    }

    private void fillSubCategoriesId(List<CategoryModel> totalList, Category category){
        try {
            List<? extends CategoryModel> subCategories = category.getChildren();
            if (subCategories.size() > 0) {
                for (CategoryModel subCategory : subCategories) {
                    totalList.add(subCategory);
                    fillSubCategoriesId(totalList, (Category) subCategory);
                }
            }
        } catch (DaoException e){
            if (e.getMessage() == "Entity is detached from DAO context")
                return;
            else throw e;
        }
    }

    @Override
    public Observable<CategoryModel> getParent(CategoryModel categoryModel) {
        return getAll();
    }

    @Override
    public Observable<CategoryModel> findCategory(final String categoryId) {
        return Observable.create(new Observable.OnSubscribe<CategoryModel>() {
            @Override
            public void call(Subscriber<? super CategoryModel> subscriber) {
                Category category = GreenDaoOpenHelper.daoSession(context).getCategoryDao().load(Utils.parseLong(categoryId));
                if (category == null){
                    category = getSpecialCategory(categoryId);
                }
                subscriber.onNext(category);
                subscriber.onCompleted();
            }
        });
    }

    private Category getSpecialCategory(String categoryId) {
        Category category;
        switch (categoryId){
            case ID_PROMOTION:
                category = new Category(Utils.parseLong(ID_PROMOTION), Category.ROOT_ID, "Promoções");
                break;
            case ID_SALE:
                category = new Category(Utils.parseLong(ID_SALE), Category.ROOT_ID, "Liquidação");
                break;
            case ID_NEW:
                category = new Category(Utils.parseLong(ID_NEW), Category.ROOT_ID, "Novidades");
                break;
            case ID_PROMOTION_AND_SALE:
                category = new Category(Utils.parseLong(ID_PROMOTION_AND_SALE), Category.ROOT_ID, "Promoções e Liquidação");
                break;
            default:
                category = new Category(Utils.parseLong(ID_OTHER), Category.ROOT_ID, "Outro");
        }

        return category;
    }

    @Override
    public Observable<CategoryModel> findCategory(final List<String> categoriesId) {
        //must parse to long to remove "+" that are on firebase
        final List<Long> myCategoriesId = new ArrayList<>();
        for (String categoryId :
                categoriesId) {
            myCategoriesId.add(Utils.parseLong(categoryId));
        }


        return getAll().filter(new Func1<CategoryModel, Boolean>() {
            @Override
            public Boolean call(CategoryModel categoryModel) {
                return myCategoriesId.contains(Utils.parseLong(categoryModel.getStringId()));
            }
        });
    }

    @Override
    public Observable<CategoryModel> mainViewCategories() {
        return getAll();
    }

    @Override
    public Observable<CategoryModel> drawerCategories() {
        return getAll();
    }



    public void removeAll(){
        GreenDaoOpenHelper.daoSession(context).getCategoryDao().deleteAll();
    }

    public void insert(List categoryList) {
        ArrayList<Category> myCategories = new ArrayList<>(categoryList.size());
        for (int i = 0; i < categoryList.size(); i++)
            myCategories.add((Category) categoryList.get(i));
        GreenDaoOpenHelper.daoSession(context).getCategoryDao().insertInTx(myCategories);
    }

    public void remove(long id) {
        GreenDaoOpenHelper.daoSession(context).getCategoryDao().deleteByKey(id);
    }

    public void insert(Object o) {
        GreenDaoOpenHelper.daoSession(context).insert(o);
    }

}
