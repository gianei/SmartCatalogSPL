package com.glsebastiany.smartcatalogspl.instanceditlanta.data.preferences;

import android.preference.PreferenceActivity;


import com.glsebastiany.smartcatalogspl.instanceditlanta.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PreferenceHeaders;

import java.util.ArrayList;
import java.util.List;

@PreferenceHeaders(R.xml.pref_headers)
@EActivity
public class ActivityPreferences extends PreferenceActivity {

    private static List<String> fragments = new ArrayList<String>();


    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);

        fragments.clear();
        for (Header header : target) {
            fragments.add(header.fragment);
        }
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return fragments.contains(fragmentName);
    }

}
