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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.grid;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.core.nucleous.Presenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func0;

public abstract class GalleryGridPresenterBase extends Presenter<GalleryGridFragmentBase> {

    private static int OBSERVABLE_ID = 0;

    @Inject
    ItemUseCases itemUseCases;

    private Observable<ItemModel> itemsObservable;

    public GalleryGridPresenterBase(){
        injectMe(this);
    }

    protected abstract void injectMe(GalleryGridPresenterBase galleryGridPresenterBase);

    protected void onCreatePresenter(Bundle savedState) {
        String query = getQueryFrom(savedState);
        if (query != null)
            if (getIsCategoryIdQueryFrom(savedState))
                itemsObservable = ObservableHelper.setupThreads(itemUseCases.allFromCategory(query).cache());
            else
                itemsObservable = ObservableHelper.setupThreads(itemUseCases.query(query).cache());
        else
            throw new RuntimeException("Category must be set in fragment args");
    }

    @Nullable
    protected abstract String getQueryFrom(Bundle savedState);

    protected abstract boolean getIsCategoryIdQueryFrom(Bundle savedState);

    @Override
    public void onAfterViews() {
        makeSubcription();
    }

    private void makeSubcription() {
        restartable(OBSERVABLE_ID,
                new Func0<Subscription>() {
                    @Override
                    public Subscription call() {
                        return itemsObservable.subscribe(new Observer<ItemModel>() {
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
                            public void onNext(ItemModel itemModel) {
                                if (getView() != null) {
                                    getView().stopLoading();
                                    getView().addItem(itemModel);
                                }
                            }
                        });
                    }
                }
        );

        if (isUnsubscribed(OBSERVABLE_ID))
            start(OBSERVABLE_ID);
    }
}
