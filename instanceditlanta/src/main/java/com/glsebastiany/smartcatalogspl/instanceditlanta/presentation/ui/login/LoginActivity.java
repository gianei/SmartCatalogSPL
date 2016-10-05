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
import android.view.View;
import android.widget.Toast;

import com.glsebastiany.smartcatalogspl.core.nucleous.MvpRxActivityBase;
import com.glsebastiany.smartcatalogspl.core.nucleous.RequiresPresenter;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.system.ActivityResultCodes;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ApplicationComponent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EActivity(R.layout.activity_login)
@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends MvpRxActivityBase<LoginPresenter> {

    public enum LoginErrorType{
        googleSignInError,
        googleUnknownSignInError,
        firebaseError,
        connectionError,
    }

    @Inject
    BaseAppDisplayFactory appDisplayFactory;

    /* A dialog that is presented until the DatabaseReference authentication finished. */
    private ProgressDialog mAuthProgressDialog;

    @ViewById(R.id.login_with_google)
    View loginButton;

    @AfterViews
    public void afterViews() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Login");
        mAuthProgressDialog.setMessage("Conectando");
        mAuthProgressDialog.setCancelable(false);

        if (getPresenter().isMakingLogin())
            mAuthProgressDialog.show();
    }

    @Click(R.id.login_with_google)
    public void onGoogleSignInClick() {

        mAuthProgressDialog.show();

        getPresenter().startLogin();

    }

    @OnActivityResult(ActivityResultCodes.RC_GOOGLE_LOGIN)
    public void onGoogleSignInActivityResult(Intent data) {
        getPresenter().handleLoginWithActivityResult(data);
    }

    protected void loginOkCallback(){
        mAuthProgressDialog.dismiss();
        appDisplayFactory.startMainActivity(this);
        finish();
    }

    protected void loginErrorCallback(LoginErrorType loginErrorType){
        mAuthProgressDialog.dismiss();
        Toast.makeText(this, getLoginErrorMessage(loginErrorType), Toast.LENGTH_LONG).show();
    }

    protected void loginErrorCallback(LoginErrorType loginErrorType, String additionalMessage){
        mAuthProgressDialog.dismiss();

        Toast.makeText(this, getLoginErrorMessage(loginErrorType) + " " + additionalMessage, Toast.LENGTH_LONG).show();
    }

    private String getLoginErrorMessage(LoginErrorType loginErrorType){
        switch (loginErrorType){
            case googleSignInError:
                return getString(R.string.google_sign_in_error);
            case googleUnknownSignInError:
                return getString(R.string.google_unknown_sign_in_error);
            case firebaseError:
                return getString(R.string.firebase_auth_error);
            case connectionError:
                return getString(R.string.connection_error);
            default:
                return "Should never happen";
        }
    }


    @Override
    protected void injectApplicationComponent() {
        AndroidApplication.<ApplicationComponent>singleton().getApplicationComponent().inject(this);
    }
}