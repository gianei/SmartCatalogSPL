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

package com.glsebastiany.smartcatalogspl.ui.tabbedgallery.swipeable;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.di.BaseFragment;
import com.glsebastiany.smartcatalogspl.di.components.DaggerItemsGroupComponent;
import com.glsebastiany.smartcatalogspl.di.components.ItemsGroupComponent;
import com.glsebastiany.smartcatalogspl.di.helper.HasComponent;
import com.glsebastiany.smartcatalogspl.di.modules.ItemsGroupModule;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.tabbedgallery.swipeable.SwipeableController;
import com.glsebastiany.smartcatalogspl.ui.tabbedgallery.swipeable.grid.FragmentGalleryGrid;
import com.glsebastiany.smartcatalogspl.ui.tabbedgallery.swipeable.grid.FragmentGalleryGrid_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;

import java.io.Serializable;

import javax.inject.Inject;

import rx.Observable;

@EFragment(R.layout.fragment_gallery_visualization)
public class FragmentGalleryVisualization extends BaseFragment
    implements HasComponent<ItemsGroupComponent> {

    @InstanceState
    boolean isFromSavedInstance = false;

    @InstanceState
    ItemObservableSerializable itemObservableSerializable;

    @FragmentArg
    String categoryId;

    @Inject
    SwipeableController swipeableController;

    private ItemsGroupComponent itemsGroupComponent;


    public static FragmentGalleryVisualization newInstance(String categoryId) {
        return FragmentGalleryVisualization_.builder().categoryId(categoryId).build();
    }

    @Override
    protected void initializeInjector() {
        getFragmentComponent().inject(this);
    }

    @AfterInject
    public void afterInject(){
        if (itemObservableSerializable == null){
            itemObservableSerializable = new ItemObservableSerializable( swipeableController.getItemsObservable(categoryId));
        }

        itemsGroupComponent = DaggerItemsGroupComponent.builder()
                .fragmentComponent(getFragmentComponent())
                .itemsGroupModule(
                        new ItemsGroupModule(itemObservableSerializable.getItemsObservable())
                )
                .build();

    }


    @AfterViews
    public void afterViews(){

        if (!isFromSavedInstance) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            FragmentGalleryGrid galleryGridFragment = FragmentGalleryGrid_.builder().build();
            fragmentTransaction.add(R.id.gallery_visualization, galleryGridFragment);
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

    public ItemsGroupComponent getComponent() {
        return itemsGroupComponent;
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
