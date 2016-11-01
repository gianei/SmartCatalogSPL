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

package com.glsebastiany.smartcatalogspl.core.domain.categorygroup;

import com.glsebastiany.smartcatalogspl.core.data.categorygroup.CategoryGroupModel;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class CategoryGroupUseCases {

    @Inject
    CategoryGroupRepository categoryGroupRepository;

    @Inject
    public CategoryGroupUseCases(){}

    public Observable<CategoryGroupModel> loadAll(){
        return Observable.create(new Observable.OnSubscribe<CategoryGroupModel>() {
            @Override
            public void call(Subscriber<? super CategoryGroupModel> subscriber) {

                List<? extends CategoryGroupModel> categoryGroups = categoryGroupRepository.loadAll();

                for (CategoryGroupModel categoryGroupModel :
                        categoryGroups) {
                    subscriber.onNext(categoryGroupModel);
                }

                subscriber.onCompleted();
            }
        });
    }

    public Observable<CategoryGroupModel> mainViewCategoriesGroups(){
        return Observable.create(new Observable.OnSubscribe<CategoryGroupModel>() {
            @Override
            public void call(Subscriber<? super CategoryGroupModel> subscriber) {

                List<? extends CategoryGroupModel> categoryGroups = categoryGroupRepository.mainViewCategoriesGroups();

                while (categoryGroups.size() == 0) {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        subscriber.onError(e);
                    }

                    categoryGroups = categoryGroupRepository.mainViewCategoriesGroups();
                }

                for (CategoryGroupModel categoryGroupModel :
                        categoryGroups) {
                    subscriber.onNext(categoryGroupModel);
                }

                subscriber.onCompleted();
            }
        });
    }

    public void insert(CategoryGroupModel categoryGroupModel){
        categoryGroupRepository.insert(categoryGroupModel);
    }

    public void insertAll(List<CategoryGroupModel> categoryGroupList){
        categoryGroupRepository.insertAll(categoryGroupList);
    }

    public void remove(String categoryId){
        categoryGroupRepository.remove(categoryId);
    }

    public void removeAll(){
        categoryGroupRepository.removeAll();
    }

}
