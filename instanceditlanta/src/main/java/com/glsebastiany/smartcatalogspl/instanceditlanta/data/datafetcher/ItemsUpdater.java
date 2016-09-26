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

package com.glsebastiany.smartcatalogspl.instanceditlanta.data.datafetcher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.glsebastiany.smartcatalogspl.core.Utils;
import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.firebase.FirebaseItem;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.preferences.SharedPreferencesUpdate_;
import com.glsebastiany.smartcatalogspl.instanceditlanta.domain.DitlantaItemUseCases;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class ItemsUpdater implements FirebaseUpdatable {
    private static final String LOG_TAG = ItemsUpdater.class.getSimpleName();

    private final SharedPreferencesUpdate_ sharedPreferencesUpdate;
    private final DitlantaItemUseCases itemUseCases;


    @Inject
    public ItemsUpdater(Context context, ItemUseCases itemUseCases) {
        this.sharedPreferencesUpdate = new SharedPreferencesUpdate_(context);
        this.itemUseCases = (DitlantaItemUseCases) itemUseCases;
    }

    @Override
    @NonNull
    public String getHashLocation() {
        return "itemsHashCode";
    }

    @Override
    @NonNull
    public String getLocation() {
        return FirebaseItem.LOCATION;
    }

    @NonNull
    public String updatedDateChildLocation() {
        return FirebaseItem.CHILD_UPDATED_DATE;
    }

    @Override
    public long getLatestUpdate() {
        return sharedPreferencesUpdate.pref_item_latest_update_key().get();
    }

    @Override
    public void saveUpdatedDate(long date){
        sharedPreferencesUpdate.pref_item_latest_update_key().put(date);
    }


    @Override
    public String getFirebaseId(DataSnapshot dataSnapshot) {
        return dataSnapshot.getKey();
    }

    @Override
    @NonNull
    public Set<String> getLocalIds(int expectedSize) {
        Set<String> localItemsIds = new LinkedHashSet<>(expectedSize);
        List<ItemModel> allItems = itemUseCases.getAll().toList().toBlocking().single();
        for (ItemModel item :
                allItems) {
            localItemsIds.add(item.getStringId());
        }
        return localItemsIds;
    }

    @Override
    public void clean() {
        itemUseCases.removeAll();
    }

    public long insertAll(DataSnapshot dataSnapshots) {
        long latestDate = 0;
        List<ItemModel> items = new ArrayList<>(5000);
        for (DataSnapshot dataSnapshot :
                dataSnapshots.getChildren()) {
            FirebaseItem firebaseItem = dataSnapshot.getValue(FirebaseItem.class);
            items.add(firebaseItem.toBaseItem());

            if (firebaseItem.getUpdatedDate() > latestDate)
                latestDate = firebaseItem.getUpdatedDate();
        }

        itemUseCases.insert(items);
        return latestDate;
    }

    @Override
    public long insert(DataSnapshot snapshot) {
        FirebaseItem firebaseItem = snapshot.getValue(FirebaseItem.class);
        Log.d(LOG_TAG, "Inserting item: " + firebaseItem.getId());

        itemUseCases.remove(firebaseItem.getId());
        itemUseCases.insert(firebaseItem.toBaseItem());

        return firebaseItem.getUpdatedDate();
    }

    @Override
    public long change(DataSnapshot snapshot) {
        FirebaseItem firebaseItem = snapshot.getValue(FirebaseItem.class);
        Log.d(LOG_TAG, "Changing item: " + firebaseItem.getId());

        itemUseCases.remove(firebaseItem.getId());
        itemUseCases.insert(firebaseItem.toBaseItem());

        return firebaseItem.getUpdatedDate();
    }

    @Override
    public long remove(DataSnapshot snapshot) {
        FirebaseItem firebaseItem = snapshot.getValue(FirebaseItem.class);

        remove(String.valueOf(firebaseItem.getId()));

        return firebaseItem.getUpdatedDate();
    }

    public void remove(String id) {
        Log.d(LOG_TAG, "Removing item: " + id);
        itemUseCases.remove(Utils.parseLong(id));
    }


    @Override
    public long move(DataSnapshot snapshot) {
        FirebaseItem firebaseItem = snapshot.getValue(FirebaseItem.class);
        Log.d(LOG_TAG, "Moving item: " + firebaseItem.getId());

        return firebaseItem.getUpdatedDate();
    }

}