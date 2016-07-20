package com.glsebastiany.smartcatalogspl.presenter.foods;


import android.view.ViewGroup;

import com.glsebastiany.smartcatalogspl.presenter.DisplayFactory;
import com.glsebastiany.smartcatalogspl.presenter.ItemViewHolder;

import javax.inject.Inject;

public class FoodDisplayFactory implements DisplayFactory {

    @Inject
    public FoodDisplayFactory(){};

    @Override
    public ItemViewHolder createItemViewHolder(ViewGroup parent) {
        return FoodViewHolder.createInstance(parent);
    }
}
