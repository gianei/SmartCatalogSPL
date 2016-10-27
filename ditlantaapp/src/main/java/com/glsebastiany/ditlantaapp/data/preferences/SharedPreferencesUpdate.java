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


import com.glsebastiany.ditlantaapp.R;

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
