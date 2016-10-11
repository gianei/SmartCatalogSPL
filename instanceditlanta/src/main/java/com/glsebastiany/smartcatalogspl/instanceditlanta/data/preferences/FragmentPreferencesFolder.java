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

import android.content.DialogInterface;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;


import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.FileServices;

import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.PreferenceByKey;
import org.androidannotations.annotations.PreferenceChange;
import org.androidannotations.annotations.PreferenceClick;
import org.androidannotations.annotations.PreferenceScreen;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;


@PreferenceScreen(R.xml.pref_folder)
@EFragment
public class FragmentPreferencesFolder extends PreferenceFragment {

    @Pref
    SharedPreferencesFolder_ sharedPreferencesFolder;

    @PreferenceByKey(R.string.pref_fotos_folder_key)
    Preference fotosFolder;

    @PreferenceChange(R.string.pref_fotos_folder_key)
    void myPrefPreferenceChanged(String newValue, Preference preference) {
        preference.setSummary(newValue);
    }

    @PreferenceClick(R.string.pref_fotos_folder_key)
    void fotosPreferenceClick(){
        final ArrayList<String> directories = FileServices.getPossibleFotosFolders(getActivity());
        int currentSelected = -1;
        for (int i = 0; i < directories.size(); i++) {
            if (directories.get(i).equals(sharedPreferencesFolder.photosFolder().get()))
                currentSelected = i;
        }
        final int[] selected = {0};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.pref_fotos_folder_title)
                .setSingleChoiceItems(directories.toArray(new CharSequence[directories.size()]), currentSelected,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selected[0] = which;
                            }
                        }
                )
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        sharedPreferencesFolder.photosFolder().put(directories.get(selected[0]));
                        fotosFolder.setSummary(directories.get(selected[0]));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                });

        builder.create().show();
    }

    @AfterPreferences
    void initPrefs(){

        fotosFolder.setSummary(sharedPreferencesFolder.photosFolder().get());

    }

}
