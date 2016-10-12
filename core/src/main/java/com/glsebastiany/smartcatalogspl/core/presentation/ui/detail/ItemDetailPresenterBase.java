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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.detail;

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

public abstract class ItemDetailPresenterBase extends Presenter<ItemDetailFragmentBase> {

    private static int OBSERVABLE_ID = 0;

    @Inject
    ItemUseCases itemUseCases;

    private Observable<ItemModel> itemsObservable;

    public ItemDetailPresenterBase(){
        injectMe(this);
    }

    protected abstract void injectMe(ItemDetailPresenterBase itemDetailPresenterBase);

    protected void onCreatePresenter(Bundle savedState) {
        String categoryId = getCategoryIdFrom(savedState);

        if (categoryId != null)
            itemsObservable = ObservableHelper.setupThreads(itemUseCases.find(categoryId).cache());
        else
            throw new RuntimeException("Item id must be set in fragment args");
    }

    @Nullable
    protected abstract String getCategoryIdFrom(Bundle savedState);

    @Override
    public void onAfterViews() {
        restartable(OBSERVABLE_ID,
                new Func0<Subscription>() {
                    @Override
                    public Subscription call() {
                        return itemsObservable.subscribe(new Observer<ItemModel>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                throw new RuntimeException(e);
                            }

                            @Override
                            public void onNext(ItemModel itemModel) {
                                if (getView() != null)
                                    getView().addItem(itemModel);
                            }
                        });
                    }
                }
        );

        if (isUnsubscribed(OBSERVABLE_ID))
            start(OBSERVABLE_ID);
    }
}
