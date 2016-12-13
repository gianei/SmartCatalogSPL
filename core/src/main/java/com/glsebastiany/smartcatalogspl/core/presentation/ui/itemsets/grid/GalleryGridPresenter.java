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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.itemsets.grid;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.core.domain.category.BaseCategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemBasicUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.mvpFramework.Presenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.Singletons;

import io.reactivex.Observable;

public class GalleryGridPresenter extends Presenter<GalleryGridFragmentBase> {

    private static int OBSERVABLE_ID = 0;


    private Observable<ItemBasicModel> itemsCompositionObservable;

    public GalleryGridPresenter() {

    }


    protected void onCreatePresenter(Bundle savedState) {
        ItemBasicUseCases itemBasicUseCases = Singletons.getInstance().itemBasicUseCases;
        BaseCategoryUseCases categoryUseCases = Singletons.getInstance().categoryUseCases;


        String query = getQueryFrom(savedState);
        if (query != null) {

            if (getIsCategoryIdQueryFrom(savedState))
                itemsCompositionObservable = categoryUseCases.allItemsFromCategory(query);
            else
                itemsCompositionObservable = itemBasicUseCases.query(query);


            itemsCompositionObservable = ObservableHelper.setupThreads(itemsCompositionObservable.cache());
        } else
            throw new RuntimeException("Category must be set in fragment args");
    }


    @Override
    public void onAfterViews() {
        makeSubcription();
    }

    private void makeSubcription() {
        restartable(OBSERVABLE_ID,
                aVoid -> itemsCompositionObservable
                        .subscribe(
                                itemComposition -> {
                                    if (getView() != null) {
                                        getView().stopLoading();
                                        getView().addItem(itemComposition);
                                    }
                                },
                                error -> {
                                    throw new RuntimeException(error);
                                },
                                () -> {
                                    if (getView() != null)
                                        getView().stopLoading();
                                }

                        )
        );

        if (isUnsubscribed(OBSERVABLE_ID))
            start(OBSERVABLE_ID);
    }

    @Nullable
    protected String getQueryFrom(Bundle savedState) {
        String query = null;


        if (savedState != null) {
            if (savedState.containsKey(GalleryGridFragment_.SEARCH_QUERY_ARG)) {
                query = savedState.getString(GalleryGridFragment_.SEARCH_QUERY_ARG);
            }
        }
        return query;
    }

    protected boolean getIsCategoryIdQueryFrom(Bundle savedState) {
        boolean isCategoryIdQuery = false;

        if (savedState != null) {
            if (savedState.containsKey(GalleryGridFragment_.IS_CATEGORY_ID_QUERY_ARG)) {
                isCategoryIdQuery = savedState.getBoolean(GalleryGridFragment_.IS_CATEGORY_ID_QUERY_ARG);
            }
        }
        return isCategoryIdQuery;
    }
}
