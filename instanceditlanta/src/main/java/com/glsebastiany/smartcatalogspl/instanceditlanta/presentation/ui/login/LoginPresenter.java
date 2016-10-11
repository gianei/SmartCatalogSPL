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


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.glsebastiany.smartcatalogspl.core.nucleous.Presenter;
import com.glsebastiany.smartcatalogspl.core.presentation.system.ActivityResultCodes;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPresenter
        extends Presenter<LoginActivity>
        implements GoogleApiClient.OnConnectionFailedListener {

    private static final String LOG_TAG = LoginPresenter.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient = null;
    private boolean isMakingLogin = false;

    public boolean isMakingLogin(){
        return isMakingLogin;
    }


    @Override
    public void onAfterViews(){
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getView().getApplicationContext().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        if (mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(getView().getApplicationContext())
                    .enableAutoManage(getView() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

    }

    public void startLogin() {
        isMakingLogin = true;
        getView().startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient), ActivityResultCodes.RC_GOOGLE_LOGIN);
    }

    public void handleLoginWithActivityResult(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        handleGoogleSignInResult(result);
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            loginWithGoogleInFirebase(result.getSignInAccount());
        } else {

            if (result.getStatus().getStatusCode() == GoogleSignInStatusCodes.SIGN_IN_CANCELLED) {
                getView().loginErrorCallback(LoginActivity.LoginErrorType.googleSignInError);
            } else {
                getView().loginErrorCallback(LoginActivity.LoginErrorType.googleUnknownSignInError,result.getStatus().getStatusMessage());
            }

            Log.d(LOG_TAG, "google login error: " + result.getStatus().getStatusMessage());
            isMakingLogin = false;
        }
    }

    public void loginWithGoogleInFirebase(GoogleSignInAccount googleAccount) {
        AuthCredential credential = GoogleAuthProvider.getCredential(googleAccount.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(getView(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        isMakingLogin = false;

                        if (task.isSuccessful()) {
                            getView().loginOkCallback();
                        } else {
                            Log.w(LOG_TAG, "firebase sign-in error: ", task.getException());
                            getView().loginErrorCallback(LoginActivity.LoginErrorType.firebaseError);
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        isMakingLogin = false;
        getView().loginErrorCallback(LoginActivity.LoginErrorType.connectionError, connectionResult.toString());
    }



}
