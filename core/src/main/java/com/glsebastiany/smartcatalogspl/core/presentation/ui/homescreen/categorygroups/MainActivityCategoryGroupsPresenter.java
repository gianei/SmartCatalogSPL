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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.homescreen.categorygroups;

import com.glsebastiany.smartcatalogspl.core.data.categorygroup.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.core.domain.categorygroup.CategoryGroupUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.mvpFramework.Presenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.Singletons;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func0;

public class MainActivityCategoryGroupsPresenter extends Presenter<MainActivityCategoryGroups> implements Func0<Subscription> {

    private static int OBSERVABLE_ID = 0;

    private Observable<CategoryGroupModel> observable;

    @Inject
    public CategoryGroupUseCases categoryGroupUseCases;

    public MainActivityCategoryGroupsPresenter(){

        categoryGroupUseCases = Singletons.getInstance().categoryGroupUseCases;

        observable = ObservableHelper.setupThreads(getCategoryGroupObservable().cache());

    }

    @Override
    protected void onAfterViews() {
        makeSubcription();
    }

    private void makeSubcription() {
        restartable(OBSERVABLE_ID, this);
        if (isUnsubscribed(OBSERVABLE_ID))
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
