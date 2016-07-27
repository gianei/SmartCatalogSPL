package com.glsebastiany.smartcatalogspl.di;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.glsebastiany.smartcatalogspl.di.components.ActivityComponent;
import com.glsebastiany.smartcatalogspl.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.di.components.DaggerFragmentComponent;
import com.glsebastiany.smartcatalogspl.di.components.FragmentComponent;
import com.glsebastiany.smartcatalogspl.di.modules.FragmentModule;

public abstract class BaseFragment extends Fragment {

    FragmentComponent fragmentComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initializeInjector();

    }

    public ApplicationComponent getApplicationComponent() {
        return ((BaseActivity)getActivity()).getApplicationComponent();
    }

    public ActivityComponent getActivityComponent(){
        return ((BaseActivity)getActivity()).getActivityComponent();
    }

    public FragmentComponent getFragmentComponent(){
        return fragmentComponent;
    }


    private void initializeInjector() {
        fragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();

        inject(fragmentComponent);
    }

    public abstract void inject(FragmentComponent fragmentComponent);
}
