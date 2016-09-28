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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.domain.ObservableHelper;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseSwipeableGalleryController;
import com.glsebastiany.smartcatalogspl.core.presentation.system.FragmentBase;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;

import java.io.Serializable;

import javax.inject.Inject;

import rx.Observable;

@EFragment(resName="fragment_gallery_visualization")
public abstract class SwipeableGalleryFragmentBase extends FragmentBase  {

    @Inject
    BaseSwipeableGalleryController swipeableController;

    @Inject
    BaseAppDisplayFactory appDisplayFactory;

    @InstanceState
    public boolean isFromSavedInstance = false;

    @InstanceState
    public ItemObservableSerializable itemObservableSerializable;

    @FragmentArg
    public String categoryId;


    @Override
    protected void injectComponent() {
        injectMe(this);
    }

    protected abstract void injectMe(SwipeableGalleryFragmentBase fragmentGalleryBase);


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
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Fragment fragment = appDisplayFactory.provideGalleryGridFragment();
            fragmentTransaction.add(R.id.gallery_visualization, fragment);
            fragmentTransaction.commit();

            isFromSavedInstance = true;
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



    public static class ItemObservableSerializable implements Serializable {

        private Observable<ItemModel> itemsObservable;

        public ItemObservableSerializable(Observable<ItemModel> itemsObservable){
            this.itemsObservable = itemsObservable;
        }

        public Observable<ItemModel> getItemsObservable() {
            return itemsObservable;
        }
    }
}
