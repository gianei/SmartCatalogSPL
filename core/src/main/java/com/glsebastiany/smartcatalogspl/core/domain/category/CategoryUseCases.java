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

import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;

import org.greenrobot.greendao.DaoException;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class CategoryUseCases {

    @Inject
    public CategoryRepository categoryRepository;

    @Inject
    public CategoryUseCases(){}

    public Observable<CategoryModel>  getAll() {
        return Observable.create(new Observable.OnSubscribe<CategoryModel>() {
            @Override
            public void call(Subscriber<? super CategoryModel> subscriber) {
                for (CategoryModel categoryModel :
                        categoryRepository.loadAll()) {
                    subscriber.onNext(categoryModel);
                }

                subscriber.onCompleted();
            }
        });
    }

    public Observable<CategoryModel> getAllChildren(final CategoryModel category) {
        return Observable.create(new Observable.OnSubscribe<CategoryModel>() {
            @Override
            public void call(Subscriber<? super CategoryModel> subscriber) {
                LinkedList<CategoryModel> categories = new LinkedList<>();
                fillSubCategoriesId(categories, category);

                for (CategoryModel categoryModel : categories) {
                    subscriber.onNext(categoryModel);
                }

                subscriber.onCompleted();
            }
        });
    }

    private void fillSubCategoriesId(List<CategoryModel> totalList, CategoryModel category){
        try {

            List<? extends CategoryModel> subCategories = category.getChildren();
            if (subCategories.size() > 0) {
                for (CategoryModel subCategory : subCategories) {
                    totalList.add(subCategory);
                    fillSubCategoriesId(totalList, subCategory);
                }
            }
        } catch (DaoException e){
            if (e.getMessage() == "Entity is detached from DAO context")
                return;
            else throw e;
        }

    }

    public Observable<CategoryModel> findCategory(final String categoryId) {
        return Observable.create(new Observable.OnSubscribe<CategoryModel>() {
            @Override
            public void call(Subscriber<? super CategoryModel> subscriber) {
                CategoryModel category = categoryRepository.load(categoryId);
                if (category == null){
                    subscriber.onError(new Throwable("Category with id " + categoryId + " not found."));
                }
                subscriber.onNext(category);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<CategoryModel> findCategory(final List<String> categoriesId) {
        if(categoriesId.size() > 0) {
            Observable<CategoryModel> categories = findCategory(categoriesId.get(0));

            for (int i = 1; i < categoriesId.size(); i++) {
                categories = categories.concatWith(findCategory(categoriesId.get(i)));
            }

            return categories;

        } else {
            return Observable.empty();
        }

    }

    public void insert(CategoryModel categoryModel){
        categoryRepository.insert(categoryModel);
    }

    public void insertAll(List<CategoryModel> categoryList){
        categoryRepository.insertAll(categoryList);
    }

    public void remove(String categoryId){
        categoryRepository.remove(categoryId);
    }

    public void removeAll(){
        categoryRepository.removeAll();
    }

}
