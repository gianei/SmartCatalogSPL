package com.glsebastiany.smartcatalogspl.presentation;

import android.view.ViewGroup;
import android.widget.BaseAdapter;

public interface DisplayFactory {
    ItemViewHolder createItemViewHolder(ViewGroup parent);
    BaseAdapter drawerAdapter();
}
