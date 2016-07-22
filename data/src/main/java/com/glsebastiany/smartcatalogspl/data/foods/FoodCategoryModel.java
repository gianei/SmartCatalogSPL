package com.glsebastiany.smartcatalogspl.data.foods;


import com.glsebastiany.smartcatalogspl.data.CategoryModel;

public class FoodCategoryModel implements CategoryModel{

    private String id;


    public FoodCategoryModel(String id){
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }


}
