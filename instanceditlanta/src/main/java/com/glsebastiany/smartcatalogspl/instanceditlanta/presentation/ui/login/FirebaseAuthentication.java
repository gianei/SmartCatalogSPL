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


import android.app.Activity;
import android.support.annotation.NonNull;

import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthentication {

    FirebaseAuth.AuthStateListener authStateListener;

    public FirebaseAuthentication(final Activity activity, final BaseAppDisplayFactory baseAppDisplayFactory){

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (userIsNotLoggedIn(user)) {
                    baseAppDisplayFactory.startLoginActivity();
                    activity.finish();
                }
            }
        };
    }

    private boolean userIsNotLoggedIn(FirebaseUser user) {
        return user == null;
    }

    public void setOnResume() {

        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    public void setOnPause() {
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
    }





















}
