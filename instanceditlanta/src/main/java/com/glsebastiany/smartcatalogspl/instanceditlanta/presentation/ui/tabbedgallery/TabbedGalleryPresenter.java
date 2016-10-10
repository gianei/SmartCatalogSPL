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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.tabbedgallery;

import android.os.Bundle;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.core.nucleous.Presenter;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ApplicationComponent;

import java.util.Arrays;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func0;

import static com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.tabbedgallery.TabbedGalleryFragment_.CATEGORIES_ID_EXTRA_ARG;

public class TabbedGalleryPresenter extends Presenter<TabbedGalleryFragment> {

    @Inject
    CategoryUseCases categoryUseCases;

    @Inject
    BaseAppDisplayFactory baseAppDisplayFactory;

    private Observable<CategoryModel> categoryModelObservable;

    private Subscription drawerSubscription;

    public TabbedGalleryPresenter(){
        AndroidApplication.<ApplicationComponent>singleton().getApplicationComponent().inject(this);
    }
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        String[] categoriesIds = null;

        if (savedState!= null) {
            if (savedState.containsKey(CATEGORIES_ID_EXTRA_ARG)) {
                categoriesIds = savedState.getStringArray(CATEGORIES_ID_EXTRA_ARG);
            }
        }

        if (categoriesIds != null)
            categoryModelObservable = ObservableHelper.setupThreads(
                    categoryUseCases.findCategory(Arrays.asList(categoriesIds)).cache()
            );
        else
            throw new RuntimeException("Categories must be set in fragment args");
    }

    @Override
    protected void onTakeView() {
        makeSubcription();
    }

    private void makeSubcription() {
        restartable(5,
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

        start(5);
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
