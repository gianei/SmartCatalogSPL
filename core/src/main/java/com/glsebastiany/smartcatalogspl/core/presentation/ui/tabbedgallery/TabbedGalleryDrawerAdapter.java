package com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery;

import android.widget.BaseAdapter;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;


public abstract class TabbedGalleryDrawerAdapter extends BaseAdapter {
    public abstract void addItem(CategoryModel categoryModel);
}
