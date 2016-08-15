package com.glsebastiany.smartcatalogspl.di.modules;


import android.support.v4.app.FragmentStatePagerAdapter;

import com.glsebastiany.smartcatalogspl.di.scopes.PerFragment;
import com.glsebastiany.smartcatalogspl.ui.gallery.CategoryItemsViewPagerAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class PageAdapterModule {

    @Provides
    @PerFragment
    FragmentStatePagerAdapter provideStatePageAdapter(CategoryItemsViewPagerAdapter viewPagerAdapter){
        return viewPagerAdapter;
    }
}
