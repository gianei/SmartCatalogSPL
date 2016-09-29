package com.glsebastiany.smartcatalogspl.instanceditlanta.data.preferences;



import com.glsebastiany.smartcatalogspl.instanceditlanta.R;

import org.androidannotations.annotations.sharedpreferences.DefaultRes;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(SharedPref.Scope.UNIQUE)
public interface SharedPreferencesZoom {
    @DefaultRes(R.integer.grid_span_start)
    int gridZoom();
}
