package com.glsebastiany.smartcatalogspl.presentation.foods;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.glsebastiany.smartcatalogspl.presentation.DisplayFactory;
import com.glsebastiany.smartcatalogspl.presentation.ItemViewHolder;
import com.glsebastiany.smartcatalogspl.presentation.GalleryGridItemsAdapter;

import javax.inject.Inject;

public class FoodDisplayFactory implements DisplayFactory {

    @Inject
    GalleryGridItemsAdapter galleryGridItemsAdapter;

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

    @Override
    public RecyclerView.Adapter<RecyclerView.ViewHolder> gritItemsAdapter() {
        return galleryGridItemsAdapter;
    }
}
