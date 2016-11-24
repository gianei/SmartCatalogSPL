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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.homescreen.categorypages;

import android.os.Bundle;

import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.core.domain.category.BaseCategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.mvpFramework.Presenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.Singletons;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;

public class MainActivityCategoryPagesPresenter extends Presenter<MainActivityCategoryPages> {

    private static int OBSERVABLE_ID = 0;

    BaseCategoryUseCases categoryUseCases;

    private Observable<CategoryModel> categoryModelObservable;

    public MainActivityCategoryPagesPresenter(){
        categoryUseCases = Singletons.getInstance().categoryUseCases;
    }


    @Override
    protected void onCreatePresenter(Bundle savedState) {

        //TODO
            categoryModelObservable = ObservableHelper.setupThreads(
                    categoryUseCases.getAll().delay(2, TimeUnit.SECONDS).skip(2).limit(5).cache());

    }

    @Override
    protected void onAfterViews() {
        makeSubcription();
    }

    private void makeSubcription() {
        restartable(OBSERVABLE_ID,
                () -> categoryModelObservable.subscribe(new Observer<CategoryModel>() {
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
                    public void onNext(CategoryModel categoryModel) {
                        if (getView() != null) {
                            getView().stopLoading();
                            getView().addItem(categoryModel);
                        }
                    }
                })
        );

        if (isUnsubscribed(OBSERVABLE_ID))
            start(OBSERVABLE_ID);
    }

}
