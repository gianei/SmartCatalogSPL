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

package com.glsebastiany.smartcatalogspl.ui;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.di.BaseActivity;
import com.glsebastiany.smartcatalogspl.presentation.controller.BaseMainController;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EActivity(R.layout.activity_main)
public class ActivityMain extends BaseActivity {

    @Inject
    BaseMainController baseMainController;

    @ViewById(R.id.progressBar)
    ProgressBar progressBar;

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @AfterViews
    public void afterViews(){
        setupToolbar();
        baseMainController.setupRecyclerView(this, progressBar, recyclerView);
    }

    private void setupToolbar() {
        //toolbar.setLogo(ContextCompat.getDrawable(this, R.drawable.image_logo));
        //toolbar.setLogoDescription(getString(R.string.menu_logo_description));
        //toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }



    @Override
    protected void initializeInjector() {

        getActivityComponent().inject(this);

    }


}
