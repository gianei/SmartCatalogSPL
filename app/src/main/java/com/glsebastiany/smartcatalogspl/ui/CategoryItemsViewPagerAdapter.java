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

package com.glsebastiany.smartcatalogspl.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.glsebastiany.smartcatalogspl.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.ui.gallery.grid.FragmentGalleryGrid_;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;


public class CategoryItemsViewPagerAdapter extends FragmentStatePagerAdapter implements Observer<CategoryModel> {

    List<CategoryModel> categories = new LinkedList<>();

    @Inject
    public CategoryItemsViewPagerAdapter(FragmentManager fm, CategoryUseCases categoryUseCases) {
        super(fm);

        categoryUseCases.mainViewCategories().subscribe(this);
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        return FragmentGalleryGrid_.builder().build();
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