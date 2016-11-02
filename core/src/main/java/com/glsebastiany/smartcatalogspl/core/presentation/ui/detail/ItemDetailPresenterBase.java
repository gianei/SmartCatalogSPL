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

import com.glsebastiany.smartcatalogspl.core.SPLConfigurator;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemComposition;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemPromotedModel;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemPromotedUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.Presenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func0;

public abstract class ItemDetailPresenterBase extends Presenter<ItemDetailFragmentBase> {

    private static int OBSERVABLE_ID = 0;

    @Inject
    ItemBasicUseCases itemBasicUseCases;

    @Inject
    ItemPromotedUseCases itemPromotedUseCases;

    @Inject
    SPLConfigurator splConfigurator;

    private Observable<ItemComposition> itemsObservable;

    public ItemDetailPresenterBase() {
        injectMe(this);
    }

    protected abstract void injectMe(ItemDetailPresenterBase itemDetailPresenterBase);

    protected void onCreatePresenter(Bundle savedState) {
        String categoryId = getCategoryIdFrom(savedState);

        if (categoryId != null) {
            Observable<ItemBasicModel> basicObservable = itemBasicUseCases.find(categoryId);

            if (splConfigurator.hasPromotedItemsFeature())
                itemsObservable = basicObservable.concatMap(itemBasicModel -> itemPromotedUseCases.load(itemBasicModel.getStringId())
                        .map(itemPromotedModel -> new ItemComposition(itemBasicModel, itemPromotedModel))
                );
            else
                itemsObservable = basicObservable.map(ItemComposition::new);

            itemsObservable = ObservableHelper.setupThreads(itemsObservable.cache());
        } else
            throw new RuntimeException("Item id must be set in fragment args");
    }

    @Nullable
    protected abstract String getCategoryIdFrom(Bundle savedState);

    @Override
    public void onAfterViews() {
        restartable(OBSERVABLE_ID,
                () -> itemsObservable.subscribe(
                        itemComposition -> {
                            if (getView() != null)
                                getView().addItem(itemComposition);
                        },
                        error -> {
                            throw new RuntimeException(error);
                        }
                )
        );

        if (isUnsubscribed(OBSERVABLE_ID))
            start(OBSERVABLE_ID);
    }
}
