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
