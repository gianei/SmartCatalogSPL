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

import com.glsebastiany.smartcatalogspl.core.data.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.instancefood.data.FoodCategoryGroupModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class FoodCategoryGroupUseCase implements CategoryGroupUseCases {

    List<FoodCategoryGroupModel> categoryGroups = new LinkedList<>();

    @Inject
    public FoodCategoryGroupUseCase(){
        categoryGroups.add(
                new FoodCategoryGroupModel("0", "Food", "https://unsplash.it/300?random",
                    new ArrayList<>(Arrays.asList("Pasta", "Breakfest"))
                )
        );

        categoryGroups.add(
                new FoodCategoryGroupModel("1", "Liquids", "https://unsplash.it/300?random",
                        new ArrayList<>(Arrays.asList("Drinks", "Alcoholic", "Juice"))
                )
        );

        categoryGroups.add(
                new FoodCategoryGroupModel("2", "All", "https://unsplash.it/300?random",
                        new ArrayList<>(Arrays.asList("Root"))
                )
        );
    }


    @Override
    public Observable<CategoryGroupModel> mainViewCategoriesGroups() {
        return Observable.create(new Observable.OnSubscribe<CategoryGroupModel>() {
            @Override
            public void call(Subscriber<? super CategoryGroupModel> subscriber) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // error
                }

                for (CategoryGroupModel categoryGroup : categoryGroups) {
                    subscriber.onNext(categoryGroup);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // error
                    }
                }

                subscriber.onCompleted();

            }
        });

    }

}
