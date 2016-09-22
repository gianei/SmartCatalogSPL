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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.glsebastiany.smartcatalogspl.core.presentation.controller.LoginController;
import com.glsebastiany.smartcatalogspl.core.presentation.system.ActivityResultCodes;

import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.AndroidApplication;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.ActivityComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.DaggerActivityComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.DaggerLoginComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.components.LoginComponent;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.modules.ActivityModule;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.di.modules.LoginModule;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;

import javax.inject.Inject;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    /* A dialog that is presented until the DatabaseReference authentication finished. */
    private ProgressDialog mAuthProgressDialog;

    LoginComponent loginComponent;

    @Inject
    LoginController loginController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityComponent activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getAndroidApplication().getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();

        mAuthProgressDialog = new ProgressDialog(this);
        loginComponent = DaggerLoginComponent.builder()
                .activityComponent(activityComponent)
                .loginModule(new LoginModule(mAuthProgressDialog))
                .build();

        loginComponent.inject(this);
    }

    @AfterViews
    public void afterViews() {

        /* Setup the progress dialog that is displayed later when authenticating with DatabaseReference */

        mAuthProgressDialog.setTitle("Login");
        mAuthProgressDialog.setMessage("Conectando");
        mAuthProgressDialog.setCancelable(false);
    }

    @Click(R.id.login_with_google)
    public void onGoogleSignInClick() {
        loginController.onSignInClick();
        mAuthProgressDialog.show();
    }

    @OnActivityResult(ActivityResultCodes.RC_GOOGLE_LOGIN)
    public void onGoogleSignInActivityResult(Intent data) {
        loginController.onActivityResult( data);
    }
    
    public void dismissProgress() {
        mAuthProgressDialog.dismiss();
    }

    public AndroidApplication getAndroidApplication(){
        return (AndroidApplication)getApplication();
    }

}