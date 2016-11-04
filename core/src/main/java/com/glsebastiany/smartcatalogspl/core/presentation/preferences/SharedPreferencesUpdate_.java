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

package com.glsebastiany.smartcatalogspl.core.presentation.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import org.androidannotations.api.sharedpreferences.EditorHelper;
import org.androidannotations.api.sharedpreferences.LongPrefEditorField;
import org.androidannotations.api.sharedpreferences.LongPrefField;
import org.androidannotations.api.sharedpreferences.SharedPreferencesHelper;
import org.androidannotations.api.sharedpreferences.StringPrefEditorField;
import org.androidannotations.api.sharedpreferences.StringPrefField;

import android.content.Context;
import android.content.SharedPreferences;

import com.glsebastiany.smartcatalogspl.core.R;

import org.androidannotations.api.sharedpreferences.EditorHelper;
import org.androidannotations.api.sharedpreferences.LongPrefEditorField;
import org.androidannotations.api.sharedpreferences.LongPrefField;
import org.androidannotations.api.sharedpreferences.SharedPreferencesHelper;
import org.androidannotations.api.sharedpreferences.StringPrefEditorField;
import org.androidannotations.api.sharedpreferences.StringPrefField;

public final class SharedPreferencesUpdate_
        extends SharedPreferencesHelper
{
    private Context context_;

    public SharedPreferencesUpdate_(Context context) {
        super(context.getSharedPreferences("SharedPreferencesUpdate", 0));
        this.context_ = context;
    }

    public SharedPreferencesUpdate_.SharedPreferencesUpdateEditor_ edit() {
        return new SharedPreferencesUpdate_.SharedPreferencesUpdateEditor_(getSharedPreferences(), context_);
    }

    /**
     * <p><b>Defaults to</b>: </p>
     *
     */
    public StringPrefField serverAddress() {
        return stringField(context_.getString(R.string.pref_server_address_key), context_.getResources().getString(R.string.pref_server_address_default));
    }

    /**
     * <p><b>Defaults to</b>: 0</p>
     *
     */
    public LongPrefField itemLatestUpdate() {
        return longField(context_.getString(R.string.pref_item_latest_update_key), 0L);
    }

    /**
     * <p><b>Defaults to</b>: 0</p>
     *
     */
    public LongPrefField categoryLatestUpdate() {
        return longField(context_.getString(R.string.pref_category_latest_update_key), 0L);
    }

    /**
     * <p><b>Defaults to</b>: 0</p>
     *
     */
    public LongPrefField suitCaseLatestUpdate() {
        return longField(context_.getString(R.string.pref_suit_case_latest_update_key), 0L);
    }

    public final static class SharedPreferencesUpdateEditor_
            extends EditorHelper<SharedPreferencesUpdateEditor_>
    {
        private Context context_;

        SharedPreferencesUpdateEditor_(SharedPreferences sharedPreferences, Context context) {
            super(sharedPreferences);
            this.context_ = context;
        }

        public StringPrefEditorField<SharedPreferencesUpdateEditor_> serverAddress() {
            return stringField(context_.getString(R.string.pref_server_address_key));
        }

        public LongPrefEditorField<SharedPreferencesUpdateEditor_> itemLatestUpdate() {
            return longField(context_.getString(R.string.pref_item_latest_update_key));
        }

        public LongPrefEditorField<SharedPreferencesUpdate_.SharedPreferencesUpdateEditor_> categoryLatestUpdate() {
            return longField(context_.getString(R.string.pref_category_latest_update_key));
        }

        public LongPrefEditorField<SharedPreferencesUpdate_.SharedPreferencesUpdateEditor_> suitCaseLatestUpdate() {
            return longField(context_.getString(R.string.pref_suit_case_latest_update_key));
        }
    }
}
