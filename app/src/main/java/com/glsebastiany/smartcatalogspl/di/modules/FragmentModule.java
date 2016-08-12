package com.glsebastiany.smartcatalogspl.di.modules;

import android.support.v4.app.FragmentManager;

import com.glsebastiany.smartcatalogspl.di.BaseActivity;
import com.glsebastiany.smartcatalogspl.di.BaseFragment;
import com.glsebastiany.smartcatalogspl.di.scopes.PerActivity;
import com.glsebastiany.smartcatalogspl.di.scopes.PerFragment;

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
}
