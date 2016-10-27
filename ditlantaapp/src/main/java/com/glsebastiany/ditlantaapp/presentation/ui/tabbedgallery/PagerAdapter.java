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

package com.glsebastiany.ditlantaapp.presentation.ui.tabbedgallery;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryPageAdapter;
import com.glsebastiany.ditlantaapp.data.db.Category;

import java.util.LinkedList;
import java.util.List;


public class PagerAdapter extends TabbedGalleryPageAdapter {

    List<CategoryModel> categories = new LinkedList<>();

    public PagerAdapter(FragmentManager fm, BaseAppDisplayFactory baseAppDisplayFactory) {
        super(fm, baseAppDisplayFactory);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Category cat = ((Category)categories.get(position));
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

    @Override
    public CategoryModel getCategoryModel(int position){
        return categories.get(position);
    }

}