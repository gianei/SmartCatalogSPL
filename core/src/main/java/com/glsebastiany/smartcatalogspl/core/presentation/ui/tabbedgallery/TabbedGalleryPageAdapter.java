package com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;

public abstract class TabbedGalleryPageAdapter extends FragmentStatePagerAdapter {
    public TabbedGalleryPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public abstract void addItem(CategoryModel categoryModel);

    public abstract CategoryModel getCategoryModel(int position);

    public abstract void performDrawerClick(CategoryModel categoryModel, int currentTabPosition);
}
