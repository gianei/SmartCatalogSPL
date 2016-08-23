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

package com.glsebastiany.smartcatalogspl.presentation.cars;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glsebastiany.smartcatalogspl.data.cars.CarItemModel;
import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.presentation.ItemViewHolder;
import com.glsebastiany.smartcatalogspl.presentation.R;

public class CarViewHolder extends ItemViewHolder {

    public TextView mTextView;

    public CarViewHolder(TextView v) {
        super(v);
        mTextView = v;
    }

    public static ItemViewHolder createInstance(ViewGroup parent){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        return new CarViewHolder((TextView)v);
    }

    @Override
    public void print(ItemModel itemModel){
        CarItemModel carItemModel = (CarItemModel) itemModel;
        mTextView.setText(carItemModel.getModel() + " " + carItemModel.getCompany() + " " + carItemModel.getYear());

    }

}
