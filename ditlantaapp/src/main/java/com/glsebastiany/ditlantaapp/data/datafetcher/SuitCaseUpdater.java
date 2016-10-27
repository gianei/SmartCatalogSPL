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


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.glsebastiany.smartcatalogspl.core.data.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryGroupUseCases;
import com.glsebastiany.ditlantaapp.data.firebase.FirebaseSuitCase;
import com.glsebastiany.ditlantaapp.data.preferences.SharedPreferencesUpdate_;
import com.glsebastiany.ditlantaapp.domain.DitlantaCategoryGroupsRepository;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;


public class SuitCaseUpdater implements FirebaseUpdatable {

    private static final String LOG_TAG = SuitCaseUpdater.class.getSimpleName();

    final protected SharedPreferencesUpdate_ sharedPreferencesUpdate;
    final protected DitlantaCategoryGroupsRepository ditlantaCategoryGroupUseCases;


    @Inject
    public SuitCaseUpdater(Context context, CategoryGroupUseCases categoryGroupUseCases) {
        this.sharedPreferencesUpdate = new SharedPreferencesUpdate_(context);
        this.ditlantaCategoryGroupUseCases = (DitlantaCategoryGroupsRepository) categoryGroupUseCases;
    }

    @NonNull
    @Override
    public String getHashLocation() {
        return FirebaseSuitCase.LOCATION;
    }

    @NonNull
    @Override
    public String getLocation() {
        return FirebaseSuitCase.LOCATION;
    }

    @NonNull
    @Override
    public String updatedDateChildLocation() {
        return FirebaseSuitCase.CHILD_UPDATED_DATE;
    }

    @Override
    public long getLatestUpdate() {
        return sharedPreferencesUpdate.suitCaseLatestUpdate().get();
    }

    @Override
    public void saveUpdatedDate(long date) {
        sharedPreferencesUpdate.suitCaseLatestUpdate().put(date);

    }

    @Override
    public String getFirebaseId(DataSnapshot dataSnapshot) {
        FirebaseSuitCase firebaseSuitCase = dataSnapshot.getValue(FirebaseSuitCase.class);
        return String.valueOf(firebaseSuitCase.getName().hashCode());
    }

    @NonNull
    @Override
    public Set<String> getLocalIds(int expectedSize) {
        Set<String> localSuitCaseIds = new LinkedHashSet<>(expectedSize);
        List<CategoryGroupModel> categoryGroupModels = ditlantaCategoryGroupUseCases.mainViewCategoriesGroups().toList().toBlocking().single();

        for (CategoryGroupModel model:
             categoryGroupModels) {
            localSuitCaseIds.add(model.getStringId());
        }

        return localSuitCaseIds;

    }

    @Override
    public void clean() {

        ditlantaCategoryGroupUseCases.removeAll();

    }

    @Override
    public long insertAll(DataSnapshot dataSnapshots) {
        long latestDate = 0;
        List<CategoryGroupModel> suitCases = new ArrayList<>(5);
        for (DataSnapshot dataSnapshot :
                dataSnapshots.getChildren()) {
            FirebaseSuitCase firebaseSuitCase = dataSnapshot.getValue(FirebaseSuitCase.class);
            suitCases.add(firebaseSuitCase.toSuitCase());

            if (firebaseSuitCase.getUpdatedDate() > latestDate)
                latestDate = firebaseSuitCase.getUpdatedDate();
        }

        ditlantaCategoryGroupUseCases.insert(suitCases);
        return latestDate;
    }

    @Override
    public long insert(DataSnapshot snapshot) {
        FirebaseSuitCase firebaseSuitCase = snapshot.getValue(FirebaseSuitCase.class);
        Log.d(LOG_TAG, "Inserting suitCase: " + firebaseSuitCase.getName());

        ditlantaCategoryGroupUseCases.remove(String.valueOf(firebaseSuitCase.getName().hashCode()));
        ditlantaCategoryGroupUseCases.insert(firebaseSuitCase.toSuitCase());

        return firebaseSuitCase.getUpdatedDate();
    }

    @Override
    public long change(DataSnapshot snapshot) {
        FirebaseSuitCase firebaseSuitCase = snapshot.getValue(FirebaseSuitCase.class);
        Log.d(LOG_TAG, "Changing suitCase: " + firebaseSuitCase.getName());

        ditlantaCategoryGroupUseCases.remove(String.valueOf(firebaseSuitCase.getName().hashCode()));
        ditlantaCategoryGroupUseCases.insert(firebaseSuitCase.toSuitCase());

        return firebaseSuitCase.getUpdatedDate();
    }

    @Override
    public long remove(DataSnapshot snapshot) {
        FirebaseSuitCase firebaseSuitCase = snapshot.getValue(FirebaseSuitCase.class);

        ditlantaCategoryGroupUseCases.remove(String.valueOf(firebaseSuitCase.getName().hashCode()));

        return firebaseSuitCase.getUpdatedDate();
    }

    @Override
    public void remove(String id) {
        Log.d(LOG_TAG, "Removing suitCase: " + id);
        ditlantaCategoryGroupUseCases.remove(id);
    }

    @Override
    public long move(DataSnapshot snapshot) {
        FirebaseSuitCase firebaseSuitCase = snapshot.getValue(FirebaseSuitCase.class);
        Log.d(LOG_TAG, "Moving suitCase: " + firebaseSuitCase.getName());

        return firebaseSuitCase.getUpdatedDate();
    }

}


