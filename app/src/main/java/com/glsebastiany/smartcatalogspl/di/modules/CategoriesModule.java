package com.glsebastiany.smartcatalogspl.di.modules;

import com.glsebastiany.smartcatalogspl.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.di.scopes.PerFragment;
import com.glsebastiany.smartcatalogspl.domain.CategoryUseCases;

import dagger.Module;
import dagger.Provides;
import rx.Observable;

@Module
public class CategoriesModule {
    private final Observable<CategoryModel> categoriesObservable;

    public CategoriesModule(CategoryUseCases categoryUseCases) {
        this.categoriesObservable = categoryUseCases.mainViewCategories();
    }

    @Provides
    @PerFragment
    Observable<CategoryModel> provideCategoryObservable(){
        return this.categoriesObservable;
    }

}
