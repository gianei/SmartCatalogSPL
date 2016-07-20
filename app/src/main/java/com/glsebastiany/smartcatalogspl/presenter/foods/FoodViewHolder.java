package com.glsebastiany.smartcatalogspl.presenter.foods;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.presenter.ItemViewHolder;

public class FoodViewHolder extends ItemViewHolder {

    public TextView mTextView;

    public FoodViewHolder(TextView v) {
        super(v);
        mTextView = v;
    }

    public static ItemViewHolder createInstance(ViewGroup parent){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        return new FoodViewHolder((TextView)v);
    }

    @Override
    public void print(ItemModel itemModel) {
        mTextView.setText(itemModel.getId());
    }
}
