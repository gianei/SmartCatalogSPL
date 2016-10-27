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

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedHashSet;
import java.util.Set;

public class FirebaseUpdater {
    private static final String LOG_TAG = FirebaseUpdater.class.getSimpleName();

    final private FirebaseUpdatable firebaseUpdatable;

    public FirebaseUpdater( final FirebaseUpdatable firebaseUpdatable) {
        this.firebaseUpdatable = firebaseUpdatable;

        if (firebaseUpdatable.getLatestUpdate() == 0) {
            addInitialListener();
        } else {
            addListenerSinceLastUpdate();
        }

        checkForRemovedItems(firebaseUpdatable);
    }

    private void addInitialListener() {
        final Query query = FirebaseDatabase.getInstance().getReference().child(firebaseUpdatable.getLocation());
        query.addValueEventListener(new InitialValueEventListener(query));

        Log.d(LOG_TAG, "SingleValue listener added");
    }

    private class InitialValueEventListener implements ValueEventListener{
        final Query query;

        private InitialValueEventListener(Query query) {
            this.query = query;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshots) {
            new AsyncTask<DataSnapshot, Void, Void>() {
                @Override
                protected Void doInBackground(DataSnapshot... params) {
                    firebaseUpdatable.clean();
                    long latestDate = firebaseUpdatable.insertAll(params[0]);

                    firebaseUpdatable.saveUpdatedDate(latestDate);

                    addListenerSinceLastUpdate();

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    query.removeEventListener(InitialValueEventListener.this);
                }
            }.execute(dataSnapshots);
        }

        @Override
        public void onCancelled(DatabaseError DatabaseError) {
            logCancel();
            query.removeEventListener(this);
        }
    }


    private void addListenerSinceLastUpdate() {
        Query query = FirebaseDatabase.getInstance().getReference()
                .child(firebaseUpdatable.getLocation())
                .orderByChild(firebaseUpdatable.updatedDateChildLocation())
                .startAt(firebaseUpdatable.getLatestUpdate() + 1);

        query.addChildEventListener(new MyChildEventListener());

        Log.d(LOG_TAG, "ChildEvent listener added");
    }


    private void checkForRemovedItems(FirebaseUpdatable firebaseUpdatable) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(firebaseUpdatable.getHashLocation());
        //singleEventValueListener just dont work: http://stackoverflow.com/questions/35840486/firebase-seems-to-use-old-data-snapshot
        ref.addValueEventListener(new RemovedItemsValueEventListener(ref));
    }

    private class RemovedItemsValueEventListener implements ValueEventListener{
        final DatabaseReference databaseReference;

        private RemovedItemsValueEventListener(DatabaseReference databaseReference) {
            this.databaseReference = databaseReference;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshots) {
            new AsyncTask<DataSnapshot, Void, Void>() {
                @Override
                protected Void doInBackground(DataSnapshot... params) {
                    removeFromHash(params[0]);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    databaseReference.removeEventListener(RemovedItemsValueEventListener.this);
                }
            }.execute(dataSnapshots);
        }

        @Override
        public void onCancelled(DatabaseError DatabaseError) {
            logCancel();
            databaseReference.removeEventListener(this);
        }
    }

    protected void removeFromHash(DataSnapshot dataSnapshots) {
        final Set<String> firebaseIds = new LinkedHashSet<>(3000);
        for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {
            firebaseIds.add(firebaseUpdatable.getFirebaseId(dataSnapshot));
        }

        Set<String> localIds = firebaseUpdatable.getLocalIds(firebaseIds.size());

        localIds.removeAll(firebaseIds);

        for (String id :  localIds) {
            firebaseUpdatable.remove(id);
        }
    }

    private class MyChildEventListener implements ChildEventListener {
        public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
            new AsyncTask<DataSnapshot, Void, Void>(){
                @Override
                protected Void doInBackground(DataSnapshot... params) {
                    checkAndSetUpdatedDate(firebaseUpdatable.insert(params[0]));
                    return null;
                }
            }.execute(snapshot);
        }

        public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
            new AsyncTask<DataSnapshot, Void, Void>(){
                @Override
                protected Void doInBackground(DataSnapshot... params) {
                    checkAndSetUpdatedDate(firebaseUpdatable.change(params[0]));
                    return null;
                }
            }.execute(snapshot);
        }

        public void onChildRemoved(DataSnapshot snapshot) {
            new AsyncTask<DataSnapshot, Void, Void>(){
                @Override
                protected Void doInBackground(DataSnapshot... params) {
                    checkAndSetUpdatedDate(firebaseUpdatable.remove(params[0]));
                    return null;
                }
            }.execute(snapshot);
        }

        public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {
            new AsyncTask<DataSnapshot, Void, Void>(){
                @Override
                protected Void doInBackground(DataSnapshot... params) {
                    checkAndSetUpdatedDate(firebaseUpdatable.move(params[0]));
                    return null;
                }
            }.execute(snapshot);
        }

        @Override
        public void onCancelled(DatabaseError DatabaseError) {
            logCancel();
        }

        private void checkAndSetUpdatedDate(long updatedDate) {
            if (updatedDate > firebaseUpdatable.getLatestUpdate())
                firebaseUpdatable.saveUpdatedDate(updatedDate);
        }
    }

    private void logCancel() {
        Log.d(LOG_TAG, "DatabaseReference update canceled");
    }
}
