package com.glsebastiany.smartcatalogspl.domain;

import com.glsebastiany.smartcatalogspl.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.data.CategoryRepository;
import com.glsebastiany.smartcatalogspl.data.foods.FoodCategoryModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class FoodCategoriesUseCases implements CategoryUseCases{

    ItemUseCases itemUseCases;
    CategoryRepository categoryRepository;

    @Inject
    public FoodCategoriesUseCases(ItemUseCases itemUseCases, CategoryRepository categoryRepository){
        this.itemUseCases = itemUseCases;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Observable<CategoryModel> mainViewCategories() {
        return categoryRepository.getDirectChildren(new FoodCategoryModel("Root"));
    }

    @Override
    public Observable<CategoryModel> drawerCategories() {
        return categoryRepository.getAll();
    }

}
