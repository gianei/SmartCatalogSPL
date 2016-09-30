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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.main;

import com.glsebastiany.smartcatalogspl.core.data.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.SuitCase;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.core.nucleous.Presenter;


import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func0;

public class MainPresenter extends Presenter<MainActivity> implements Func0<Subscription> {

    @Inject
    CategoryGroupUseCases categoryGroupUseCases;

    @Inject
    BaseAppDisplayFactory baseAppDisplayFactory;


    private Observable<CategoryGroupModel> observable;



    public MainPresenter(){


        AndroidApplication.singleton.getApplicationComponent().inject(this);

        observable = ObservableHelper.setupThreads(getCategoryGroupObservable().cache());


    }


    @Override
    protected void onTakeView() {

        makeSubcription();
    }

    private void makeSubcription() {
        restartable(5, this);
        start(5);
    }

    private Observable<CategoryGroupModel> getCategoryGroupObservable() {
        return categoryGroupUseCases.mainViewCategoriesGroups();
    }

    public void refresh() {
        observable = ObservableHelper.setupThreads(getCategoryGroupObservable().cache());
        makeSubcription();
    }

    @Override
    public Subscription call() {
        return observable.subscribe(new Observer<CategoryGroupModel>() {
            @Override
            public void onCompleted() {
                getView().stopLoading();
            }

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException(e);
            }

            @Override
            public void onNext(CategoryGroupModel categoryGroupModel) {
                getView().addItem((SuitCase)categoryGroupModel);
            }
        });
    }
}
