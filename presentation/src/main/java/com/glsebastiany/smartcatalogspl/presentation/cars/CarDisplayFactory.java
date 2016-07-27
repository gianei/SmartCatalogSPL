package com.glsebastiany.smartcatalogspl.presentation.cars;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.glsebastiany.smartcatalogspl.presentation.DisplayFactory;
import com.glsebastiany.smartcatalogspl.presentation.ItemViewHolder;
import com.glsebastiany.smartcatalogspl.presentation.GalleryGridItemsAdapter;

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

    @Override
    public BaseAdapter drawerAdapter() {
        throw new RuntimeException("Not implemented yet");
    }


}
