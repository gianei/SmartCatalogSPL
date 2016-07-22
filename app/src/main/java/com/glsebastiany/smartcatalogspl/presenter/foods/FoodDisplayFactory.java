package com.glsebastiany.smartcatalogspl.presenter.foods;


import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.glsebastiany.smartcatalogspl.presenter.DisplayFactory;
import com.glsebastiany.smartcatalogspl.presenter.ItemViewHolder;

import javax.inject.Inject;

public class FoodDisplayFactory implements DisplayFactory {

    @Inject
    FoodDrawerAdapter foodDrawerAdapter;

    @Inject
    public FoodDisplayFactory(){};

    @Override
    public ItemViewHolder createItemViewHolder(ViewGroup parent) {
        return FoodViewHolder.createInstance(parent);
    }

    @Override
    public BaseAdapter drawerAdapter() {
        return foodDrawerAdapter;
    }
}
