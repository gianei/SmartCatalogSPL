package com.glsebastiany.smartcatalogspl.presentation;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public interface DisplayFactory {
    ItemViewHolder createItemViewHolder(ViewGroup parent);
    BaseAdapter drawerAdapter();
    RecyclerView.Adapter<RecyclerView.ViewHolder> gritItemsAdapter();
}
