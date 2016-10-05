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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.detail;

import android.os.Bundle;

import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.core.nucleous.Presenter;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ApplicationComponent;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func0;

import static com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.detail.ItemDetailFragment_.ITEM_ID_ARG;

public class ItemDetailPresenter extends Presenter<ItemDetailFragment> {

    private static int OBSERVABLE_ID = 0;

    @Inject
    ItemUseCases itemUseCases;

    private Observable<ItemModel> itemsObservable;

    public ItemDetailPresenter(){
        AndroidApplication.<ApplicationComponent>singleton().getApplicationComponent().inject(this);
    }

    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        String categoryId = null;

        if (savedState!= null) {
            if (savedState.containsKey(ITEM_ID_ARG)) {
                categoryId = savedState.getString(ITEM_ID_ARG);
            }
        }
        if (categoryId != null)
            itemsObservable = ObservableHelper.setupThreads(itemUseCases.find(categoryId).cache());
        else
            throw new RuntimeException("Item id must be set in fragment args");
    }

    @Override
    public void onTakeView() {
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

        start(OBSERVABLE_ID);
    }

}
