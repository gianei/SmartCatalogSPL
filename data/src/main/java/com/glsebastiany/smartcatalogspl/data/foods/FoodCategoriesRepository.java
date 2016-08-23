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

package com.glsebastiany.smartcatalogspl.data.foods;

import android.os.Handler;

import com.glsebastiany.smartcatalogspl.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.data.CategoryRepository;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class FoodCategoriesRepository implements CategoryRepository{

    List<CategoryModel> items = new LinkedList<>();

    @Inject
    public FoodCategoriesRepository(){
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
}
