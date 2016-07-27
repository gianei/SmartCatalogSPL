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
