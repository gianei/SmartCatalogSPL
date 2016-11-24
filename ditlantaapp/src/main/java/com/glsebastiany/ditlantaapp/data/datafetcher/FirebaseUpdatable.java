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

package com.glsebastiany.ditlantaapp.data.datafetcher;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;

import java.util.Set;

public interface FirebaseUpdatable {

    @NonNull
    String getHashLocation();

    @NonNull
    String getLocation();

    @NonNull
    String updatedDateChildLocation();

    long getLatestUpdate();

    void saveUpdatedDate(long date);

    String getFirebaseId(DataSnapshot dataSnapshot);

    @NonNull
    Set<String> getLocalIds(int expectedSize);

    void clean();

    long insertAll(DataSnapshot dataSnapshots);

    long insert(DataSnapshot snapshot);

    long change(DataSnapshot snapshot);

    long remove(DataSnapshot snapshot);

    void remove(String id);

    long move(DataSnapshot snapshot);

}
