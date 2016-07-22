package com.glsebastiany.smartcatalogspl.data;

import rx.Observable;

public interface CategoryRepository {
    Observable<CategoryModel> getAll();
    Observable<CategoryModel> getDirectChildren(CategoryModel categoryModel);
    Observable<CategoryModel> getAllChildren(CategoryModel categoryModel);
    Observable<CategoryModel> getParent(CategoryModel categoryModel);
}
