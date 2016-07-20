package com.glsebastiany.smartcatalogspl.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.glsebastiany.smartcatalogspl.data.ItemModel;


public abstract class ItemViewHolder extends RecyclerView.ViewHolder {
    public ItemViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void print(ItemModel itemModel);
}
