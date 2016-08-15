package com.glsebastiany.smartcatalogspl.ui;


import android.support.v4.app.FragmentStatePagerAdapter;

import com.glsebastiany.smartcatalogspl.presentation.FragmentDisplayFactory;

import javax.inject.Inject;

public class FoodFragmentDisplayFactory implements FragmentDisplayFactory {

    @Inject
    CategoryItemsViewPagerAdapter categoryItemsViewPagerAdapter;

    @Inject
    public FoodFragmentDisplayFactory(){};


    @Override
    public FragmentStatePagerAdapter categoriesPagerAdapter() {
        return categoryItemsViewPagerAdapter;
    }
}
