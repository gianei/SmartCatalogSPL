
package com.glsebastiany.smartcatalogspl.di.modules;

import android.content.Context;

import com.glsebastiany.smartcatalogspl.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.data.CategoryRepository;
import com.glsebastiany.smartcatalogspl.data.cars.CarItemsItemRepository;
import com.glsebastiany.smartcatalogspl.data.ItemRepository;
import com.glsebastiany.smartcatalogspl.data.foods.FoodCategoriesRepository;
import com.glsebastiany.smartcatalogspl.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.domain.FerrariItemUseCases;
import com.glsebastiany.smartcatalogspl.domain.FoodCategoriesUseCases;
import com.glsebastiany.smartcatalogspl.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.presentation.DisplayFactory;
import com.glsebastiany.smartcatalogspl.presentation.foods.FoodDisplayFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ItemRepository provideItemRepository(CarItemsItemRepository itemRepository){
        return itemRepository;
    }

    @Provides
    @Singleton
    ItemUseCases provideUseCases(FerrariItemUseCases itemUseCases){
        return itemUseCases;
    }

    @Provides
    @Singleton
    CategoryRepository provideCategoryRepository(FoodCategoriesRepository categoryRepository){
        return categoryRepository;
    }

    @Provides
    @Singleton
    CategoryUseCases provideCategoryUseCases(FoodCategoriesUseCases categoriyUseCases){
        return categoriyUseCases;
    }

    @Provides
    @Singleton
    DisplayFactory provideDisplayFactory(FoodDisplayFactory displayFactory){
        return displayFactory;
    }

}
