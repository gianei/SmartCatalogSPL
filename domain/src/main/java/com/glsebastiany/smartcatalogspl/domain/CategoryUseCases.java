package com.glsebastiany.smartcatalogspl.domain;

import com.glsebastiany.smartcatalogspl.data.CategoryModel;

import rx.Observable;

public interface CategoryUseCases {

    Observable<CategoryModel> mainViewCategories();
    Observable<CategoryModel> drawerCategories();
}
