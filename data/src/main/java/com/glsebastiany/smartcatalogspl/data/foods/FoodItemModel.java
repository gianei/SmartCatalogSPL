package com.glsebastiany.smartcatalogspl.data.foods;


import com.glsebastiany.smartcatalogspl.data.ItemModel;

public class FoodItemModel implements ItemModel {

    private String id;

    public FoodItemModel(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
