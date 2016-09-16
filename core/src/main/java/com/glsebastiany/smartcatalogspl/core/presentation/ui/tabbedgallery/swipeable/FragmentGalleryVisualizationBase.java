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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.swipeable;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseSwipeableController;
import com.glsebastiany.smartcatalogspl.core.presentation.system.FragmentBase;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;

import java.io.Serializable;

import javax.inject.Inject;

import rx.Observable;


public abstract class FragmentGalleryVisualizationBase extends FragmentBase  {

    @InstanceState
    boolean isFromSavedInstance = false;

    @InstanceState
    public ItemObservableSerializable itemObservableSerializable;

    @FragmentArg
    String categoryId;

    @Inject
    BaseSwipeableController swipeableController;



    @Override
    protected void injectComponent() {
        injectMe(this);
    }

    protected abstract void injectMe(FragmentGalleryVisualizationBase fragmentGalleryBase);


    @AfterInject
    public void afterInject(){
        if (itemObservableSerializable == null){
            itemObservableSerializable = new ItemObservableSerializable( swipeableController.getItemsObservable(categoryId));
        }

        innerAfterInject();
    }

    protected abstract void innerAfterInject();

    @AfterViews
    public void afterViews(){

        if (!isFromSavedInstance) {
            /*FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            FragmentGalleryGrid galleryGridFragment = FragmentGalleryGrid_.builder().build();
            fragmentTransaction.add(R.id.gallery_visualization, galleryGridFragment);
            fragmentTransaction.commit();

            isFromSavedInstance = true;*/
        }

        getChildFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                LinearLayout galleryTabs = (LinearLayout) getActivity().findViewById(R.id.gallery_tabs);
                if (galleryTabs != null) {
                    if (galleryTabs.getVisibility() == View.VISIBLE)
                        galleryTabs.setVisibility(View.GONE);
                    else
                        galleryTabs.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        swipeableController.endSubscriptions();
    }



    protected static class ItemObservableSerializable implements Serializable {

        private Observable<ItemModel> itemsObservable;

        public ItemObservableSerializable(Observable<ItemModel> itemsObservable){
            this.itemsObservable = itemsObservable;
        }

        public Observable<ItemModel> getItemsObservable() {
            return itemsObservable;
        }
    }
}
