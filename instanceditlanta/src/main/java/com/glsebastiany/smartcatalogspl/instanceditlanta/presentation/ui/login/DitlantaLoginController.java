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


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.LoginController;
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

import javax.inject.Inject;

public class DitlantaLoginController
        extends LoginController
        implements GoogleApiClient.OnConnectionFailedListener {

    protected AppCompatActivity mActivity;

    protected ProgressDialog mProgressDialog;

    private BaseAppDisplayFactory baseAppDisplayFactory;

    private static final String LOG_TAG = DitlantaLoginController.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;


    @Inject
    public DitlantaLoginController(AppCompatActivity mActivity, ProgressDialog mProgressDialog, BaseAppDisplayFactory baseAppDisplayFactory){
        this.mActivity = mActivity;
        this.mProgressDialog = mProgressDialog;
        this.baseAppDisplayFactory = baseAppDisplayFactory;

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mActivity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .enableAutoManage(mActivity /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onSignInClick() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mActivity.startActivityForResult(signInIntent, ActivityResultCodes.RC_GOOGLE_LOGIN);
    }

    @Override
    public void onActivityResult(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        handleGoogleSignInResult(result);
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            loginWithGoogleInFirebase(result.getSignInAccount());

        } else {
            mProgressDialog.dismiss();

            if (result.getStatus().getStatusCode() == GoogleSignInStatusCodes.SIGN_IN_CANCELLED) {
                showErrorToast(mActivity.getString(R.string.google_sign_in_error));
            } else {
                showErrorToast(mActivity.getString(R.string.google_unknown_sign_in_error) + result.getStatus().getStatusMessage());
            }

            Log.d(LOG_TAG, "google login error: " + result.getStatus().getStatusMessage());
        }
    }

    public void loginWithGoogleInFirebase(GoogleSignInAccount googleAccount) {
        AuthCredential credential = GoogleAuthProvider.getCredential(googleAccount.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressDialog.dismiss();;

                        if (task.isSuccessful()) {
                            /* Go to main activity */
                            baseAppDisplayFactory.startMainActivity(mActivity);
                            mActivity.finish();
                        } else {
                            Log.w(LOG_TAG, "firebase sign-in error: ", task.getException());
                            Toast.makeText(mActivity, R.string.firebase_auth_error,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mProgressDialog.dismiss();

        showErrorToast(connectionResult.toString());

    }

    /**
     * Show error toast to users
     */
    private void showErrorToast(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
    }
}