package com.glsebastiany.smartcatalogspl.core.presentation.ui.grid;

import android.support.v7.widget.RecyclerView;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.data.ItemModel;

public abstract class GalleryGridItemsAdapterBase extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected GalleryGridCallbacks galleryGridCallbacks;

    public GalleryGridItemsAdapterBase(GalleryGridCallbacks galleryGridCallbacks) {
        super();
        this.galleryGridCallbacks = galleryGridCallbacks;
    }

    public abstract void addItem(ItemModel itemModel);

    public abstract int findCategoryPositionInItems(CategoryModel categoryModel);

    public abstract String[] toStringArray();
}
