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

import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.Presenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.splash.SplashScreenBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.splash.SplashScreenExtended;

import rx.Observable;

public class ItemDetailPresenterBase extends Presenter<ItemDetailFragmentBaseBasic> {

    private static int OBSERVABLE_ID = 0;


    private Observable<ItemBasicModel> itemsObservable;


    protected void onCreatePresenter(Bundle savedState) {
        String categoryId = getCategoryIdFrom(savedState);

        ItemBasicUseCases itemBasicUseCases = SplashScreenBase.getInstance().itemBasicUseCases;


        if (categoryId != null) {
            itemsObservable = itemBasicUseCases.find(categoryId);

        } else
            throw new RuntimeException("Item id must be set in fragment args");
    }

    @Nullable
    protected String getCategoryIdFrom(Bundle savedState) {
        String categoryId = null;



        if (savedState!= null) {
            if (savedState.containsKey(ItemDetailFragmentBaseBasic_.ITEM_ID_ARG)) {
                categoryId = savedState.getString(ItemDetailFragmentBaseBasic_.ITEM_ID_ARG);
            }
        }
        return categoryId;
    }

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
