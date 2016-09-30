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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.login;


import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.glsebastiany.smartcatalogspl.instanceditlanta.data.datafetcher.CategoryUpdater;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.datafetcher.FirebaseUpdater;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.datafetcher.ItemsUpdater;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.datafetcher.SuitCaseUpdater;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.firebase.FirebaseUidMapping;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

public class LoginAuthStateListener implements FirebaseAuth.AuthStateListener {

    private boolean updadersStarted = false;

    @Inject
    SuitCaseUpdater suitCaseUpdater;

    @Inject
    CategoryUpdater categoryUpdater;

    @Inject
    ItemsUpdater itemsUpdater;

    @Inject
    public LoginAuthStateListener(){
    }

    @Override
    public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        if (userIsLoggedIn(user) && !updadersStarted) {
            updadersStarted = true;

            new MyAsyncTask().execute();

            saveLoginUser(user);
        }
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            new FirebaseUpdater(itemsUpdater);
            new FirebaseUpdater(categoryUpdater);
            //new FirebaseUpdater(suitCaseUpdater);
            return null;
        }
    }

    private boolean userIsLoggedIn(FirebaseUser user) {
        return user != null;
    }

    private void saveLoginUser(com.google.firebase.auth.FirebaseUser user) {
        if (user != null) {
            String email = user.getEmail();
            email = email.replaceAll("\\.",",");

            String name = user.getDisplayName();

            saveUidMapping(user.getUid(), email);
            saveUser(email, new com.glsebastiany.smartcatalogspl.instanceditlanta.data.firebase.FirebaseUser(name));
        }

    }

    private void saveUidMapping(String uid, String email) {
        FirebaseDatabase.getInstance().getReference().child(FirebaseUidMapping.LOCATION).child(uid).setValue(email);
    }

    private void saveUser(String email, com.glsebastiany.smartcatalogspl.instanceditlanta.data.firebase.FirebaseUser firebaseUser) {
        FirebaseDatabase.getInstance().getReference().child(com.glsebastiany.smartcatalogspl.instanceditlanta.data.firebase.FirebaseUser.LOCATION).child(email).setValue(firebaseUser);
    }
}
