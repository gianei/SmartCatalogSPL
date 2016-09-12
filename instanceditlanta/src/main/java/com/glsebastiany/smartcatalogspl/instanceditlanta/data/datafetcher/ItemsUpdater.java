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

import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.firebase.FirebaseItem;
import com.google.firebase.database.DataSnapshot;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

public class ItemsUpdater implements FirebaseUpdatable {
    private static final String LOG_TAG = ItemsUpdater.class.getSimpleName();

    //final protected SharedPreferencesUpdate_ sharedPreferencesUpdate;
    private ItemUseCases itemUseCases;


    @Inject
    public ItemsUpdater(Context context, ItemUseCases itemUseCases) {
        //this.sharedPreferencesUpdate = new SharedPreferencesUpdate_(context);
        this.itemUseCases = itemUseCases;
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

        return 0; //sharedPreferencesUpdate.itemLatestUpdate().get();
    }

    @Override
    public void saveUpdatedDate(long date){
        //sharedPreferencesUpdate.itemLatestUpdate().put(date);
    }


    @Override
    public String getFirebaseId(DataSnapshot dataSnapshot) {
        return "0"; //CompatHelper.parseLong(dataSnapshot.getKey());
    }

    @Override
    @NonNull
    public Set<String> getLocalIds(int expectedSize) {
        Set<String> localItemsIds = new LinkedHashSet<>(expectedSize);
        /*List<BaseItem> allItems = applicationComponent.provideBaseItemRepository().getAll();
        for (BaseItem item :
                allItems) {
            localItemsIds.add(item.getId());
        }*/
        return localItemsIds;
    }

    @Override
    public void clean() {
        //applicationComponent.provideBaseItemRepository().removeAll();
    }

    public long insertAll(DataSnapshot dataSnapshots) {
        long latestDate = 0;
        /*List<BaseItem> items = new ArrayList<>(5000);
        for (DataSnapshot dataSnapshot :
                dataSnapshots.getChildren()) {
            FirebaseItem firebaseItem = dataSnapshot.getValue(FirebaseItem.class);
            items.add(firebaseItem.toBaseItem());

            if (firebaseItem.getUpdatedDate() > latestDate)
                latestDate = firebaseItem.getUpdatedDate();
        }

        applicationComponent.provideBaseItemRepository().insert(items);*/
        return latestDate;
    }

    @Override
    public long insert(DataSnapshot snapshot) {
        FirebaseItem firebaseItem = snapshot.getValue(FirebaseItem.class);
        Log.d(LOG_TAG, "Inserting item: " + firebaseItem.getId());

        //applicationComponent.provideBaseItemRepository().remove(firebaseItem.getId());
        //applicationComponent.provideBaseItemRepository().insert(firebaseItem.toBaseItem());

        return firebaseItem.getUpdatedDate();
    }

    @Override
    public long change(DataSnapshot snapshot) {
        FirebaseItem firebaseItem = snapshot.getValue(FirebaseItem.class);
        Log.d(LOG_TAG, "Changing item: " + firebaseItem.getId());

        //applicationComponent.provideBaseItemRepository().remove(firebaseItem.getId());
        //applicationComponent.provideBaseItemRepository().insert(firebaseItem.toBaseItem());

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
        //applicationComponent.provideBaseItemRepository().remove(id);
    }


    @Override
    public long move(DataSnapshot snapshot) {
        FirebaseItem firebaseItem = snapshot.getValue(FirebaseItem.class);
        Log.d(LOG_TAG, "Moving item: " + firebaseItem.getId());

        return firebaseItem.getUpdatedDate();
    }

}