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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseTabbedGalleryController;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.Category;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.tabbedgallery.swipeable.SwipeableGalleryFragment;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Observer;


public class PagerAdapter extends FragmentStatePagerAdapter implements Observer<CategoryModel>, BaseTabbedGalleryController.DrawerClickSupport {

    private final BaseAppDisplayFactory baseAppDisplayFactory;
    List<CategoryModel> categories = new LinkedList<>();
    SparseArray<SwipeableGalleryFragment> registeredFragments = new SparseArray<>();

    public PagerAdapter(FragmentManager fm, Observable<CategoryModel> categoriesObservable, BaseAppDisplayFactory baseAppDisplayFactory) {
        super(fm);
        this.baseAppDisplayFactory = baseAppDisplayFactory;

        categoriesObservable.subscribe(this);
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        return baseAppDisplayFactory.provideGalleryVisualizationFragment(categories.get(position).getStringId());
    }

    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        Category cat = ((Category)categories.get(position));
        return cat.getName();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SwipeableGalleryFragment fragment = (SwipeableGalleryFragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    /*public int getCategoryPosition(long baseCategoryId){
        for(int i = 0; i < categoryUiItems.getCount(); i++){
            if (get(i).getStringId() == baseCategoryId)
                return i;
        }
        return -1;
    }*/

    @Override
    public int getCount() {
        return categories.size();
    }



    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(CategoryModel categoryModel) {
        categories.add(categoryModel);
        notifyDataSetChanged();
    }


    @Override
    public void performDrawerClick(CategoryModel categoryModel, int currentTabPosition) {
        registeredFragments.get(currentTabPosition).moveToSubCategorySection(categoryModel);
    }
}