/*
 *     SmartCatalogSPL, an Android catalog Software Product Line
 *     Copyright (c) 2016 Gianei Leandro Sebastiany
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.glsebastiany.ditlantaapp.data.preferences;

import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;


import com.glsebastiany.ditlantaapp.R;

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
