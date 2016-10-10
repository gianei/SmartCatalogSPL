package com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery;

import android.content.Context;
import android.widget.BaseAdapter;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;


public abstract class TabbedGalleryDrawerAdapter extends BaseAdapter {
    protected Context context;
    protected CategoryModel parentCategory;

    public TabbedGalleryDrawerAdapter(CategoryModel parentCategory, Context context) {
        this.parentCategory = parentCategory;
        this.context = context;
    }

    public abstract void addItem(CategoryModel categoryModel);
}
