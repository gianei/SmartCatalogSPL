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

package com.glsebastiany.smartcatalogspl.core.presentation.nucleous;

import android.os.Parcel;

class ParcelFn {

    private static final ClassLoader CLASS_LOADER = ParcelFn.class.getClassLoader();

    static <T> T unmarshall(byte[] array) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(array, 0, array.length);
        parcel.setDataPosition(0);
        Object value = parcel.readValue(CLASS_LOADER);
        parcel.recycle();
        return (T)value;
    }

    static byte[] marshall(Object o) {
        Parcel parcel = Parcel.obtain();
        parcel.writeValue(o);
        byte[] result = parcel.marshall();
        parcel.recycle();
        return result;
    }
}
