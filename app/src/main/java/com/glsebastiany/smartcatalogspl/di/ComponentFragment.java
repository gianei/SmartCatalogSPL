package com.glsebastiany.smartcatalogspl.di;

import android.support.v4.app.Fragment;

public abstract class ComponentFragment extends Fragment {

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>)getActivity()).getComponent());
    }
}
