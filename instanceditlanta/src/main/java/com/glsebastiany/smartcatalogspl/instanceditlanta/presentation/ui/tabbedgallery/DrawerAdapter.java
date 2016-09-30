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


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryDrawerAdapter;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.Category;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Observer;

public class DrawerAdapter extends TabbedGalleryDrawerAdapter {

    private static final int TEXT_VIEW_LEFT_PADDING_DP = 32;

    private Context context;
    private List<Category> categories = new LinkedList<>();
    private Category parentCategory;

    public DrawerAdapter(Context context, CategoryModel parentCategory) {
        this.context = context;
        this.parentCategory = (Category) parentCategory;
    }


    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).getStringId().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        view = ((LayoutInflater)context.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.view_gallery_drawer_list_item, parent, false);

        ((TextView) view).setText(categories.get(position).getName());
        if(categories.get(position).getParentId() != parentCategory.getId())
            view.setPadding((int) (TEXT_VIEW_LEFT_PADDING_DP * context.getResources().getDisplayMetrics().density),0 ,0 ,0 );

        return view;
    }

    @Override
    public void addItem(CategoryModel categoryModel){
        categories.add((Category)categoryModel);
        notifyDataSetChanged();
    }

}
