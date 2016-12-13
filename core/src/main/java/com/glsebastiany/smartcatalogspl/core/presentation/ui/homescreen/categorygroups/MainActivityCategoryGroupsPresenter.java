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

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.ResourceObserver;

public class MainActivityCategoryGroupsPresenter extends Presenter<MainActivityCategoryGroups> implements Function<Void, Disposable> {

    private static int OBSERVABLE_ID = 0;
    @Inject
    public CategoryGroupUseCases categoryGroupUseCases;
    private Observable<CategoryGroupModel> observable;

    public MainActivityCategoryGroupsPresenter() {

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

    public Disposable apply(Void aVoid) throws Exception {
        return observable.subscribeWith(new ResourceObserver<CategoryGroupModel>() {
            @Override
            public void onComplete() {
                if (getView() != null)
                    getView().stopLoading();
            }

            @Override
            public void onError(Throwable e) {
                //TODO dont do this
                //throw new RuntimeException(e);
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
