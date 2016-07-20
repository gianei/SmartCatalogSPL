package com.glsebastiany.smartcatalogspl.data.cars;


import com.glsebastiany.smartcatalogspl.data.ItemModel;

public class CarItemModel implements ItemModel {

    private String id;
    private String model;
    private String company;
    private int year;

    public CarItemModel(String id, String model, String company, int year) {
        this.id = id;
        this.model = model;
        this.company = company;
        this.year = year;
    }


    @Override
    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getCompany() {
        return company;
    }

    public int getYear() {
        return year;
    }
}
