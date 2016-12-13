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

import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.core.domain.category.BaseCategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.mvpFramework.Presenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.Singletons;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.ResourceObserver;

public class TabbedGalleryPresenterBase extends Presenter<TabbedGalleryFragment> {

    private static int OBSERVABLE_ID = 0;

    BaseCategoryUseCases categoryUseCases;

    private Observable<CategoryModel> categoryModelObservable;
    private Disposable drawerDisposable;

    public TabbedGalleryPresenterBase() {
        categoryUseCases = Singletons.getInstance().categoryUseCases;
    }


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
    protected String[] getCategoriesIdFrom(Bundle savedState) {
        String[] categoriesIds = null;

        if (savedState != null) {
            if (savedState.containsKey(TabbedGalleryFragment_.CATEGORIES_ID_EXTRA_ARG)) {
                categoriesIds = savedState.getStringArray(TabbedGalleryFragment_.CATEGORIES_ID_EXTRA_ARG);
            }
        }

        return categoriesIds;
    }

    @Override
    protected void onAfterViews() {
        makeSubcription();
    }

    private void makeSubcription() {
        restartable(OBSERVABLE_ID,
                new Function<Void, Disposable>() {
                    @Override
                    public Disposable apply(Void aVoid) throws Exception {
                        return categoryModelObservable.subscribeWith(new ResourceObserver<CategoryModel>() {
                            @Override
                            public void onComplete() {
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

        Observable<CategoryModel> allChildren = ObservableHelper.setupThreads(categoryUseCases.getAllChildren(categoryModel));

        if (drawerDisposable != null) {
            remove(drawerDisposable);
        }

        drawerDisposable = allChildren.subscribe(new Consumer<CategoryModel>() {
            @Override
            public void accept(CategoryModel categoryModel) throws Exception {
                getView().addDrawerItem(categoryModel);
            }
        });

        add(drawerDisposable);

    }
}
