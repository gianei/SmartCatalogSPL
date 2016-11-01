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

import com.glsebastiany.ditlantaapp.data.firebase.FirebaseItem;
import com.glsebastiany.ditlantaapp.data.preferences.SharedPreferencesUpdate_;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemPromotedModel;
import com.glsebastiany.smartcatalogspl.core.domain.item.ItemPromotedUseCases;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class ItemsPromotedUpdater implements FirebaseUpdatable {
    private static final String LOG_TAG = ItemsPromotedUpdater.class.getSimpleName();

    private final SharedPreferencesUpdate_ sharedPreferencesUpdate;
    private final ItemPromotedUseCases itemPromotedUseCases;


    @Inject
    public ItemsPromotedUpdater(Context context, ItemPromotedUseCases itemBasicUseCases) {
        this.sharedPreferencesUpdate = new SharedPreferencesUpdate_(context);
        this.itemPromotedUseCases = itemBasicUseCases;
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
        return sharedPreferencesUpdate.itemLatestUpdate().get();
    }

    @Override
    public void saveUpdatedDate(long date){
        sharedPreferencesUpdate.itemLatestUpdate().put(date);
    }


    @Override
    public String getFirebaseId(DataSnapshot dataSnapshot) {
        return dataSnapshot.getKey();
    }

    @Override
    @NonNull
    public Set<String> getLocalIds(int expectedSize) {
        Set<String> localItemsIds = new LinkedHashSet<>(expectedSize);
        List<ItemPromotedModel> allItems = itemPromotedUseCases.getAll().toList().toBlocking().single();
        for (ItemPromotedModel item :
                allItems) {
            localItemsIds.add(item.getStringId());
        }
        return localItemsIds;
    }

    @Override
    public void clean() {
        itemPromotedUseCases.removeAll();
    }

    public long insertAll(DataSnapshot dataSnapshots) {
        long latestDate = 0;
        List<ItemPromotedModel> items = new ArrayList<>(5000);
        for (DataSnapshot dataSnapshot :
                dataSnapshots.getChildren()) {
            FirebaseItem firebaseItem = dataSnapshot.getValue(FirebaseItem.class);
            items.add(firebaseItem.toPromotedItemModel());

            if (firebaseItem.getUpdatedDate() > latestDate)
                latestDate = firebaseItem.getUpdatedDate();
        }

        itemPromotedUseCases.insertAll(items);
        return latestDate;
    }

    @Override
    public long insert(DataSnapshot snapshot) {
        FirebaseItem firebaseItem = snapshot.getValue(FirebaseItem.class);
        Log.d(LOG_TAG, "Inserting item: " + firebaseItem.getId());

        itemPromotedUseCases.remove(String.valueOf(firebaseItem.getId()));
        itemPromotedUseCases.insert(firebaseItem.toPromotedItemModel());

        return firebaseItem.getUpdatedDate();
    }

    @Override
    public long change(DataSnapshot snapshot) {
        FirebaseItem firebaseItem = snapshot.getValue(FirebaseItem.class);
        Log.d(LOG_TAG, "Changing item: " + firebaseItem.getId());

        itemPromotedUseCases.remove(String.valueOf(firebaseItem.getId()));
        itemPromotedUseCases.insert(firebaseItem.toPromotedItemModel());

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
        itemPromotedUseCases.remove(id);
    }


    @Override
    public long move(DataSnapshot snapshot) {
        FirebaseItem firebaseItem = snapshot.getValue(FirebaseItem.class);
        Log.d(LOG_TAG, "Moving item: " + firebaseItem.getId());

        return firebaseItem.getUpdatedDate();
    }

}