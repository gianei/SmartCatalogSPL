package com.glsebastiany.smartcatalogspl.core.presentation.ui.grid;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;

public interface GalleryGridCallbacks {
    void moveToSubCategorySection(CategoryModel categoryModel);
    void switchToItemView( int position);
}
