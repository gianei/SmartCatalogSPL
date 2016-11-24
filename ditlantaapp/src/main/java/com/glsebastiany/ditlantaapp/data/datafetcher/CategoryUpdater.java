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

import com.glsebastiany.ditlantaapp.data.preferences.SharedPreferencesUpdate_;
import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.domain.category.CategoryUseCases;
import com.glsebastiany.ditlantaapp.data.firebase.FirebaseCategory;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.category.CategoryEntity;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;


public class CategoryUpdater implements FirebaseUpdatable {

    private static final String LOG_TAG = CategoryUpdater.class.getSimpleName();

    private final SharedPreferencesUpdate_ sharedPreferencesUpdate;
    private final CategoryUseCases categoryUseCases;


    @Inject
    public CategoryUpdater(Context context, CategoryUseCases categoryUseCases) {
        this.sharedPreferencesUpdate = new SharedPreferencesUpdate_(context);
        this.categoryUseCases = categoryUseCases;
    }

    @NonNull
    @Override
    public String getHashLocation() {
        return "categoriesHashCode";
    }

    @NonNull
    @Override
    public String getLocation() {
        return FirebaseCategory.LOCATION;
    }

    @NonNull
    @Override
    public String updatedDateChildLocation() {
        return FirebaseCategory.CHILD_UPDATED_DATE;
    }

    @Override
    public long getLatestUpdate() {

        return sharedPreferencesUpdate.categoryLatestUpdate().get();
    }

    @Override
    public void saveUpdatedDate(long date) {
        sharedPreferencesUpdate.categoryLatestUpdate().put(date);

    }

    @Override
    public String getFirebaseId(DataSnapshot dataSnapshot) {
        return dataSnapshot.getKey();
    }

    @NonNull
    @Override
    public Set<String> getLocalIds( int expectedSize) {
        Set<String> localCategoriesIds = new LinkedHashSet<>(expectedSize);
        List<CategoryModel> allCategories = categoryUseCases.getAll().toList().toBlocking().single();
        for (CategoryModel baseCategory :
                allCategories) {
            localCategoriesIds.add(baseCategory.getStringId());
        }
        return localCategoriesIds;
    }

    @Override
    public void clean() {
        categoryUseCases.removeAll();
        categoryUseCases.insert(new CategoryEntity(CategoryModel.ROOT_ID, null, "Root Category"));
    }

    @Override
    public long insertAll(DataSnapshot dataSnapshots) {
        long latestDate = 0;

        List<CategoryModel> categories = new ArrayList<>(2000);
        for (DataSnapshot dataSnapshot :
                dataSnapshots.getChildren()) {
            FirebaseCategory firebaseCategory = dataSnapshot.getValue(FirebaseCategory.class);
            categories.add(firebaseCategory.toBaseCategory());

            if (firebaseCategory.getUpdatedDate() > latestDate)
                latestDate = firebaseCategory.getUpdatedDate();
        }

        categoryUseCases.insertAll(categories);

        return latestDate;
    }

    @Override
    public long insert(DataSnapshot snapshot) {
        FirebaseCategory firebaseCategory = snapshot.getValue(FirebaseCategory.class);
        Log.d(LOG_TAG, "Inserting category: " + firebaseCategory.getId());

        categoryUseCases.remove(String.valueOf(firebaseCategory.getId()));
        categoryUseCases.insert(firebaseCategory.toBaseCategory());

        return firebaseCategory.getUpdatedDate();
    }

    @Override
    public long change(DataSnapshot snapshot) {
        FirebaseCategory firebaseCategory = snapshot.getValue(FirebaseCategory.class);
        Log.d(LOG_TAG, "Changing category: " + firebaseCategory.getId());

        categoryUseCases.remove(String.valueOf(firebaseCategory.getId()));
        categoryUseCases.insert(firebaseCategory.toBaseCategory());

        return firebaseCategory.getUpdatedDate();
    }

    @Override
    public long remove(DataSnapshot snapshot) {
        FirebaseCategory firebaseCategory = snapshot.getValue(FirebaseCategory.class);

        remove(String.valueOf(firebaseCategory.getId()));

        return firebaseCategory.getUpdatedDate();
    }

    @Override
    public void remove(String id) {
        if (id == "0"){ //do not remove root here
            return;
        }

        Log.d(LOG_TAG, "Removing category: " + id);
        categoryUseCases.remove(id);
    }

    @Override
    public long move(DataSnapshot snapshot) {
        FirebaseCategory firebaseCategory = snapshot.getValue(FirebaseCategory.class);
        Log.d(LOG_TAG, "Moving category: " + firebaseCategory.getId());

        return firebaseCategory.getUpdatedDate();
    }

}


