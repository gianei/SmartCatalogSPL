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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.dimodules;

import android.app.ProgressDialog;

import com.glsebastiany.smartcatalogspl.core.presentation.controller.LoginController;
import com.glsebastiany.smartcatalogspl.core.presentation.scopes.PerController;
import com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.login.DitlantaLoginController;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    protected ProgressDialog mProgressDialog;

    public LoginModule(ProgressDialog progressDialog){
        this.mProgressDialog = progressDialog;
    }


    @Provides
    @PerController
    ProgressDialog progressDialog() {
        return this.mProgressDialog;
    }

    @Provides
    @PerController
    LoginController loginController(DitlantaLoginController loginController){
        return loginController;
    }
}
