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

package com.glsebastiany.ditlantaapp.presentation.authentication;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.glsebastiany.smartcatalogspl.core.presentation.authentication.Authenticator;
import com.glsebastiany.ditlantaapp.data.datafetcher.CategoryGroupsUpdater;
import com.glsebastiany.ditlantaapp.data.datafetcher.CategoryUpdater;
import com.glsebastiany.ditlantaapp.data.datafetcher.FirebaseUpdater;
import com.glsebastiany.ditlantaapp.data.datafetcher.ItemExtendedUpdater;
import com.glsebastiany.ditlantaapp.data.datafetcher.ItemsBasicUpdater;
import com.glsebastiany.ditlantaapp.data.firebase.FirebaseUidMapping;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.login.LoginActivity_;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

public class FirebaseAuthenticator implements Authenticator {

    @Inject
    Context context;

    @Inject
    CategoryGroupsUpdater categoryGroupsUpdater;

    @Inject
    CategoryUpdater categoryUpdater;

    @Inject
    ItemsBasicUpdater itemsBasicUpdater;

    @Inject
    ItemExtendedUpdater itemExtendedUpdater;

    private FirebaseAuth.AuthStateListener authStateListener;

    private boolean updadersStarted = false;
    private boolean isNotLoggedInLoginScreen = false;

    @Inject
    public FirebaseAuthenticator() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (FirebaseAuthenticator.this.userIsNotLoggedIn(user)) {
                    if (!isNotLoggedInLoginScreen) {
                        isNotLoggedInLoginScreen = true;
                        FirebaseAuthenticator.this.openLogin();
                    }
                } else {
                    isNotLoggedInLoginScreen = false;
                    if (!updadersStarted) {
                        updadersStarted = true;
                        FirebaseAuthenticator.this.saveLoginUser(user);
                        FirebaseAuthenticator.this.startUpdates();
                    }
                }
            }
        };
    }

    private boolean userIsNotLoggedIn(FirebaseUser user) {
        return user == null;
    }

    @Override
    public void onResume() {
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    @Override
    public void onPause() {
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
    }

    @Override
    public void openLogin() {
        LoginActivity_
                .intent(context)
                .flags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION)
                .start();
    }

    private void startUpdates() {
        new FirebaseUpdater(itemsBasicUpdater);
        new FirebaseUpdater(itemExtendedUpdater);
        new FirebaseUpdater(categoryUpdater);
        new FirebaseUpdater(categoryGroupsUpdater);
    }

    private void saveLoginUser(com.google.firebase.auth.FirebaseUser user) {
        if (user != null) {
            String email = user.getEmail();
            email = email.replaceAll("\\.", ",");

            String name = user.getDisplayName();

            saveUidMapping(user.getUid(), email);
            saveUser(email, new com.glsebastiany.ditlantaapp.data.firebase.FirebaseUser(name));
        }
    }

    private void saveUidMapping(String uid, String email) {
        FirebaseDatabase.getInstance().getReference().child(FirebaseUidMapping.LOCATION).child(uid).setValue(email);
    }

    private void saveUser(String email, com.glsebastiany.ditlantaapp.data.firebase.FirebaseUser firebaseUser) {
        FirebaseDatabase.getInstance().getReference().child(com.glsebastiany.ditlantaapp.data.firebase.FirebaseUser.LOCATION).child(email).setValue(firebaseUser);
    }
}
