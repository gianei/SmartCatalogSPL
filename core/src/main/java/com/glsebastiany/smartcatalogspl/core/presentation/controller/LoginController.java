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

package com.glsebastiany.smartcatalogspl.core.presentation.controller;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.system.ActivityResultCodes;


public abstract class LoginController {

    protected final AppCompatActivity mActivity;
    private final View loginButton;
    private final ProgressDialog mProgressDialog;
    private final BaseAppDisplayFactory baseAppDisplayFactory;

    public LoginController(
            AppCompatActivity mActivity,
            View loginButton,
            ProgressDialog mProgressDialog,
            BaseAppDisplayFactory baseAppDisplayFactory){

        this.mActivity = mActivity;
        this.loginButton = loginButton;
        this.mProgressDialog = mProgressDialog;
        this.baseAppDisplayFactory = baseAppDisplayFactory;

        mProgressDialog.setTitle("Login");
        mProgressDialog.setMessage("Conectando");
        mProgressDialog.setCancelable(false);

        loginButton.setOnClickListener(new LoginClickListener());
    }

    private class LoginClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            mProgressDialog.show();
            mActivity.startActivityForResult(getSignInIntent(), ActivityResultCodes.RC_GOOGLE_LOGIN);
        }
    }

    public void onActivityResult(Intent intent){
        handleLoginWithActivityResult(intent);
    }

    protected void loginOkCallback(){
        mProgressDialog.dismiss();
        baseAppDisplayFactory.startMainActivity(mActivity);
        mActivity.finish();
    }

    protected void loginErrorCallback(String message){
        mProgressDialog.dismiss();
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
    }

    public abstract Intent getSignInIntent();

    public abstract void handleLoginWithActivityResult(Intent data);

}
