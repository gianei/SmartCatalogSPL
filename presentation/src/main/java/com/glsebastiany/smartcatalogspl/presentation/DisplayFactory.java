package com.glsebastiany.smartcatalogspl.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public interface DisplayFactory {
    ItemViewHolder drawerViewHolder(ViewGroup parent);
    BaseAdapter drawerAdapter();
    RecyclerView.Adapter<RecyclerView.ViewHolder> galleryItemsAdapter();
}
