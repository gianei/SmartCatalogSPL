package com.glsebastiany.smartcatalogspl.presenter.cars;


import android.view.ViewGroup;

import com.glsebastiany.smartcatalogspl.presenter.DisplayFactory;
import com.glsebastiany.smartcatalogspl.presenter.ItemViewHolder;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CarDisplayFactory implements DisplayFactory {

    @Inject
    public CarDisplayFactory(){}

    @Override
    public ItemViewHolder createItemViewHolder(ViewGroup parent) {
        return CarViewHolder.createInstance(parent);
    }


}
