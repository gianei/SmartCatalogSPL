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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.main;

import com.glsebastiany.smartcatalogspl.core.data.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.core.nucleous.Presenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func0;

public abstract class MainPresenterBase extends Presenter<MainActivityBase> implements Func0<Subscription> {

    private static int OBSERVABLE_ID = 0;

    private Observable<CategoryGroupModel> observable;

    @Inject
    public CategoryGroupUseCases categoryGroupUseCases;

    public MainPresenterBase(){

        injectMe(this);

        observable = ObservableHelper.setupThreads(getCategoryGroupObservable().cache());

    }

    protected abstract void injectMe(MainPresenterBase mainPresenterBase);

    @Override
    protected void onTakeView() {
        makeSubcription();
    }

    private void makeSubcription() {
        if(getView()!= null)
            getView().clear();
        restartable(OBSERVABLE_ID, this);
        start(OBSERVABLE_ID);
    }


    public Subscription call() {
        return observable.subscribe(new Observer<CategoryGroupModel>() {
            @Override
            public void onCompleted() {
                if (getView() != null)
                    getView().stopLoading();
            }

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException(e);
            }

            @Override
            public void onNext(CategoryGroupModel categoryGroupModel) {
                if (getView() != null)
                    getView().addItem(categoryGroupModel);
            }
        });
    }

    private Observable<CategoryGroupModel> getCategoryGroupObservable() {
        return categoryGroupUseCases.mainViewCategoriesGroups();
    }
}
