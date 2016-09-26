package com.glsebastiany.smartcatalogspl.instanceditlanta.data.preferences;

import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;


import com.glsebastiany.smartcatalogspl.instanceditlanta.R;

import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.PreferenceByKey;
import org.androidannotations.annotations.PreferenceChange;
import org.androidannotations.annotations.PreferenceScreen;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@PreferenceScreen(R.xml.pref_update)
@EFragment
public class FragmentPreferencesUpdate extends PreferenceFragment {

    @Pref
    SharedPreferencesUpdate_ sharedPreferencesUpdate;

    @PreferenceByKey(R.string.pref_server_address_key)
    EditTextPreference serverAddressPreference;

    @PreferenceByKey(R.string.pref_item_latest_update_key)
    Preference itemUpdatedPreference;

    @PreferenceByKey(R.string.pref_category_latest_update_key)
    Preference categoryUpdatedPreference;

    @PreferenceByKey(R.string.pref_suit_case_latest_update_key)
    Preference suitCaseUpdatedPreference;

    @PreferenceChange(R.string.pref_server_address_key)
    void myPrefPreferenceChanged(String newValue, Preference preference) {
        preference.setSummary(newValue);
    }

    @AfterPreferences
    void initPrefs(){

        serverAddressPreference.setSummary(serverAddressPreference.getText());

        itemUpdatedPreference.setSummary(epochToDateString(sharedPreferencesUpdate.itemLatestUpdate().get()));
        categoryUpdatedPreference.setSummary(epochToDateString(sharedPreferencesUpdate.categoryLatestUpdate().get()));
        suitCaseUpdatedPreference.setSummary(epochToDateString(sharedPreferencesUpdate.suitCaseLatestUpdate().get()));
    }

    public static String epochToDateString(long epochMiliseconds) {
        Date updatedate = new Date(epochMiliseconds);
        DateFormat format = SimpleDateFormat.getDateTimeInstance();
        return format.format(updatedate);
    }
}
