package com.glsebastiany.smartcatalogspl.instanceditlanta.data.preferences;


import com.glsebastiany.smartcatalogspl.instanceditlanta.R;

import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.DefaultRes;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(SharedPref.Scope.UNIQUE)
public interface SharedPreferencesUpdate {

    @DefaultRes(value = R.string.pref_server_address_default, keyRes = R.string.pref_server_address_key)
    String serverAddress();

    @DefaultLong(value = 0, keyRes = R.string.pref_item_latest_update_key)
    long itemLatestUpdate();

    @DefaultLong(value = 0, keyRes = R.string.pref_category_latest_update_key)
    long categoryLatestUpdate();

    @DefaultLong(value = 0, keyRes = R.string.pref_suit_case_latest_update_key)
    long suitCaseLatestUpdate();
}
