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

import com.glsebastiany.smartcatalogspl.core.R;

import org.androidannotations.api.sharedpreferences.EditorHelper;
import org.androidannotations.api.sharedpreferences.IntPrefEditorField;
import org.androidannotations.api.sharedpreferences.IntPrefField;
import org.androidannotations.api.sharedpreferences.SharedPreferencesHelper;


public final class SharedPreferencesZoom_
        extends SharedPreferencesHelper
{
    private Context context_;

    public SharedPreferencesZoom_(Context context) {
        super(context.getSharedPreferences("SharedPreferencesZoom", 0));
        this.context_ = context;
    }

    public SharedPreferencesZoom_.SharedPreferencesZoomEditor_ edit() {
        return new SharedPreferencesZoom_.SharedPreferencesZoomEditor_(getSharedPreferences());
    }

    /**
     * <p><b>Defaults to</b>: 0</p>
     *
     */
    public IntPrefField gridZoom() {
        return intField("gridZoom", context_.getResources().getInteger(R.integer.grid_span_start));
    }

    public final static class SharedPreferencesZoomEditor_
            extends EditorHelper<SharedPreferencesZoomEditor_>
    {

        SharedPreferencesZoomEditor_(SharedPreferences sharedPreferences) {
            super(sharedPreferences);
        }

        public IntPrefEditorField<SharedPreferencesZoomEditor_> gridZoom() {
            return intField("gridZoom");
        }
    }
}
