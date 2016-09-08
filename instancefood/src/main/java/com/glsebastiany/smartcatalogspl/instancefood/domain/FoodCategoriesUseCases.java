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

package com.glsebastiany.smartcatalogspl.instancefood.domain;

import android.os.Handler;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.instancefood.data.FoodCategoryModel;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class FoodCategoriesUseCases implements CategoryUseCases {

    List<CategoryModel> items = new LinkedList<>();

    @Inject
    public FoodCategoriesUseCases(){
        items.add(new FoodCategoryModel("Root"));
        items.add(new FoodCategoryModel("Pasta"));
        items.add(new FoodCategoryModel("Breakfest"));
        items.add(new FoodCategoryModel("Drinks"));
        items.add(new FoodCategoryModel("Alcoholic"));
        items.add(new FoodCategoryModel("Juice"));
        items.add(new FoodCategoryModel("Sweet"));
        items.add(new FoodCategoryModel("Android"));

    }

    @Override
    public Observable<CategoryModel> getAll() {
        return Observable.create(new Observable.OnSubscribe<CategoryModel>(){
            @Override
            public void call(final Subscriber<? super CategoryModel> subscriber) {
                try {
                    for (CategoryModel item :
                            items) {
                        subscriber.onNext(item);
                    }

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            subscriber.onCompleted();
                        }
                    }, 2000);
                } catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<CategoryModel> getAllChildren(final CategoryModel categoryModel) {
        return Observable.create(new Observable.OnSubscribe<CategoryModel>(){
            @Override
            public void call(final Subscriber<? super CategoryModel> subscriber) {
                try {
                    switch (categoryModel.getId()){
                        case "Drinks":
                            subscriber.onNext(items.get(4));
                            subscriber.onNext(items.get(5));
                        case "Sweet":
                            subscriber.onNext(items.get(7));
                        case "Root":
                            for (int i = 1; i < items.size(); i++)
                                subscriber.onNext(items.get(i));
                    }

                    subscriber.onCompleted();

                } catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<CategoryModel> getDirectChildren(final CategoryModel categoryModel) {
        return Observable.create(new Observable.OnSubscribe<CategoryModel>(){
            @Override
            public void call(final Subscriber<? super CategoryModel> subscriber) {
                try {
                    switch (categoryModel.getId()){
                        case "Drinks":
                            subscriber.onNext(items.get(4));
                            subscriber.onNext(items.get(5));
                        case "Sweet":
                            subscriber.onNext(items.get(7));
                        case "Root":
                            subscriber.onNext(items.get(1));
                            subscriber.onNext(items.get(2));
                            subscriber.onNext(items.get(3));
                            subscriber.onNext(items.get(6));
                    }

                    subscriber.onCompleted();

                } catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<CategoryModel> getParent(CategoryModel categoryModel) {
        return null;
    }

    @Override
    public Observable<CategoryModel> findCategory(final String categoryId) {
        return Observable.create(new Observable.OnSubscribe<CategoryModel>(){
            @Override
            public void call(final Subscriber<? super CategoryModel> subscriber) {
                try {
                    boolean found = false;
                    for (int i = 0; i<items.size();i++) {
                        if (categoryId.compareTo(items.get(i).getId()) == 0) {
                            subscriber.onNext(items.get(i));
                            found = true;
                        }
                    }

                    if (!found)
                        subscriber.onError(new Throwable("Category not found!"));

                    subscriber.onCompleted();

                } catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });

    }

    @Override
    public Observable<CategoryModel> findCategory(final List<String> categoriesId) {
        return Observable.create(new Observable.OnSubscribe<CategoryModel>(){
            @Override
            public void call(final Subscriber<? super CategoryModel> subscriber) {
                try {
                    boolean found = false;
                    for (int i = 0; i <items.size(); i++) {
                        for (int j = 0; j< categoriesId.size(); j++) {
                            if (categoriesId.get(j).compareTo(items.get(i).getId()) == 0){
                                subscriber.onNext(items.get(i));
                                found = true;
                            }
                        }
                    }

                    if (!found)
                        subscriber.onError(new Throwable("Category not found!"));

                    subscriber.onCompleted();

                } catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });

    }

    @Override
    public Observable<CategoryModel> mainViewCategories() {
        return getDirectChildren(new FoodCategoryModel("Root"));
    }

    @Override
    public Observable<CategoryModel> drawerCategories() {
        return getAll();
    }

}
