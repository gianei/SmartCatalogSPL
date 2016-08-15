package com.glsebastiany.smartcatalogspl.di.modules;

import android.support.v4.app.FragmentManager;

import com.glsebastiany.smartcatalogspl.di.BaseFragment;
import com.glsebastiany.smartcatalogspl.di.scopes.PerFragment;
import com.glsebastiany.smartcatalogspl.presentation.FragmentDisplayFactory;
import com.glsebastiany.smartcatalogspl.ui.FoodFragmentDisplayFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private final BaseFragment fragment;

    public FragmentModule(BaseFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @PerFragment
    BaseFragment fragment() {
        return this.fragment;
    }

    @Provides
    @PerFragment
    FragmentManager provideFragmentManager() { return fragment.getChildFragmentManager();}

    @Provides
    @PerFragment
    FragmentDisplayFactory provideFragmentDisplayFactory(FoodFragmentDisplayFactory displayFactory){
        return displayFactory;
    }
}
