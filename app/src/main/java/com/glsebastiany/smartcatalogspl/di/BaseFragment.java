package com.glsebastiany.smartcatalogspl.di;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.glsebastiany.smartcatalogspl.di.components.ActivityComponent;
import com.glsebastiany.smartcatalogspl.di.components.ApplicationComponent;

public abstract class BaseFragment extends Fragment {

    public AndroidApplication getAndroidApplication(){
        return getBaseActivity().getAndroidApplication();
    }

    public ApplicationComponent getApplicationComponent() {
        return getBaseActivity().getApplicationComponent();
    }

    public ActivityComponent getActivityComponent(){
        return getBaseActivity().getActivityComponent();
    }

    public BaseActivity getBaseActivity(){
        return (BaseActivity) getActivity();
    }


}
