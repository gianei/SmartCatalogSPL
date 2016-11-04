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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridCallbacks;

import java.util.LinkedList;
import java.util.List;

public class TabbedGalleryPageAdapter extends FragmentStatePagerAdapter {
    protected final BaseAppDisplayFactory baseAppDisplayFactory;
    protected SparseArray<GalleryGridCallbacks> registeredFragments = new SparseArray<>();List<CategoryModel> categories = new LinkedList<>();

    public TabbedGalleryPageAdapter(FragmentManager fm, BaseAppDisplayFactory baseAppDisplayFactory) {
        super(fm);
        this.baseAppDisplayFactory = baseAppDisplayFactory;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        return (Fragment) baseAppDisplayFactory.provideGalleryGridFragment(getCategoryModel(position).getStringId(), true);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        GalleryGridCallbacks fragment = (GalleryGridCallbacks) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CategoryModel cat = categories.get(position);
        return cat.getName();
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    public void addItem(CategoryModel categoryModel) {
        categories.add(categoryModel);
        notifyDataSetChanged();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        //workaround to make onMenuOption get called on every fragment of this pager
        for (int i = 0; i < registeredFragments.size(); i++){
            Fragment fragment = (Fragment) registeredFragments.get(registeredFragments.keyAt(i));
            fragment.setMenuVisibility(true);
        }
    }

    public CategoryModel getCategoryModel(int position){
        return categories.get(position);
    }

    public void performDrawerClick(CategoryModel categoryModel, int currentTabPosition) {
        registeredFragments.get(currentTabPosition).moveToSubCategorySection(categoryModel);
    }
}
