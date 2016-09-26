package com.glsebastiany.smartcatalogspl.instanceditlanta.data.preferences;


import com.glsebastiany.smartcatalogspl.instanceditlanta.R;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(SharedPref.Scope.UNIQUE)
public interface SharedPreferencesFolder {

    @DefaultString(value = "", keyRes = R.string.pref_fotos_folder_key)
    String photosFolder();
}
