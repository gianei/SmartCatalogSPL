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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.tabbedgallery;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Observer;


public class PagerAdapter extends FragmentStatePagerAdapter implements Observer<CategoryModel> {

    private final BaseAppDisplayFactory baseAppDisplayFactory;
    List<CategoryModel> categories = new LinkedList<>();

    public PagerAdapter(FragmentManager fm, Observable<CategoryModel> categoriesObservable, BaseAppDisplayFactory baseAppDisplayFactory) {
        super(fm);
        this.baseAppDisplayFactory = baseAppDisplayFactory;

        categoriesObservable.subscribe(this);
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        return baseAppDisplayFactory.provideGalleryFragment(categories.get(position).getId());
    }

    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getId();
    }

    /*public int getCategoryPosition(long baseCategoryId){
        for(int i = 0; i < categoryUiItems.getCount(); i++){
            if (get(i).getId() == baseCategoryId)
                return i;
        }
        return -1;
    }*/

    // This method return the Number of slidingTabLayout for the slidingTabLayout Strip

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
}