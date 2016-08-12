package com.glsebastiany.smartcatalogspl.di;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.glsebastiany.smartcatalogspl.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.di.components.DaggerFragmentComponent;
import com.glsebastiany.smartcatalogspl.di.components.FragmentComponent;
import com.glsebastiany.smartcatalogspl.di.modules.FragmentModule;

public abstract class BaseFragment extends Fragment {

    protected FragmentComponent fragmentComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();

        this.initializeInjector();
    }

    public AndroidApplication getAndroidApplication(){
        return getBaseActivity().getAndroidApplication();
    }

    public ApplicationComponent getApplicationComponent() {
        return getBaseActivity().getApplicationComponent();
    }

    public FragmentComponent getFragmentComponent(){
        return fragmentComponent;
    }


    public BaseActivity getBaseActivity(){
        return (BaseActivity) getActivity();
    }

    protected abstract void initializeInjector();

}
