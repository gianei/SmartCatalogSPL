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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.Presenter;

import java.util.Arrays;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func0;

public abstract class TabbedGalleryPresenterBase extends Presenter<TabbedGalleryFragmentBase> {

    private static int OBSERVABLE_ID = 0;

    @Inject
    CategoryUseCases categoryUseCases;

    private Observable<CategoryModel> categoryModelObservable;
    private Subscription drawerSubscription;

    public TabbedGalleryPresenterBase(){
        injectMe(this);
    }

    protected abstract void injectMe(TabbedGalleryPresenterBase tabbedGalleryPresenterBase);

    @Override
    protected void onCreatePresenter(Bundle savedState) {
        String[] categoriesIds = getCategoriesIdFrom(savedState);

        if (categoriesIds != null)
            categoryModelObservable = ObservableHelper.setupThreads(
                    categoryUseCases.findCategory(Arrays.asList(categoriesIds)).cache()
            );
        else
            throw new RuntimeException("Categories must be set in fragment args");
    }

    @Nullable
    protected abstract String[] getCategoriesIdFrom(Bundle savedState);

    @Override
    protected void onAfterViews() {
        makeSubcription();
    }

    private void makeSubcription() {
        restartable(OBSERVABLE_ID,
                new Func0<Subscription>() {
                    @Override
                    public Subscription call() {
                        return categoryModelObservable.subscribe(new Observer<CategoryModel>() {
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
                                    getView().addPageItem(categoryModel);
                                }
                            }
                        });
                    }
                }
        );

        if (isUnsubscribed(OBSERVABLE_ID))
            start(OBSERVABLE_ID);
    }

    public void findDrawerCategories(CategoryModel categoryModel) {

        Observable<CategoryModel> allChildren = ObservableHelper.setupThreads(categoryUseCases.getAllChildren(categoryModel).onBackpressureBuffer());

        if (drawerSubscription!= null ){
            remove(drawerSubscription);
        }

        drawerSubscription = allChildren.subscribe(new Action1<CategoryModel>() {
            @Override
            public void call(CategoryModel categoryModel) {
                getView().addDrawerItem(categoryModel);
            }
        });

        add(drawerSubscription);

    }
}
