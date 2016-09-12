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

import com.glsebastiany.smartcatalogspl.core.data.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.GreenDaoOpenHelper;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.SuitCase;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.SuitCaseDao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class DitlantaCategoryGroupsRepository implements CategoryGroupUseCases {

    private final Context context;

    @Inject
    public DitlantaCategoryGroupsRepository(Context context){
        this.context = context;
    }

    @Override
    public Observable<CategoryGroupModel> mainViewCategoriesGroups() {
        return ObservableHelper.createThreaded(new Observable.OnSubscribe<CategoryGroupModel>() {
            @Override
            public void call(Subscriber<? super CategoryGroupModel> subscriber) {
                SuitCaseDao suitCaseDao = GreenDaoOpenHelper.daoSession(context).getSuitCaseDao();
                for (CategoryGroupModel categoryGroupModel :
                        suitCaseDao.queryBuilder()
                                .orderAsc(SuitCaseDao.Properties.Order)
                                .list()) {
                    subscriber.onNext(categoryGroupModel);
                }

                subscriber.onCompleted();
            }
        });
    }


    public void removeAll(){
        GreenDaoOpenHelper.daoSession(context).getSuitCaseDao().deleteAll();
    }

    public void insert(List suitCaseList) {
        ArrayList<SuitCase> mySuitCases = new ArrayList<>(suitCaseList.size());
        for (int i = 0; i < suitCaseList.size(); i++)
            mySuitCases.add((SuitCase) suitCaseList.get(i));
        GreenDaoOpenHelper.daoSession(context).getSuitCaseDao().insertInTx(mySuitCases);
    }

    public void remove(String id) {
        GreenDaoOpenHelper.daoSession(context).getSuitCaseDao().deleteByKey(Long.parseLong(id));
    }

    public void insert(Object suitCase) {
        GreenDaoOpenHelper.daoSession(context).insert(suitCase);
    }


}
