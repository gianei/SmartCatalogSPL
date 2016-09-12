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

package com.glsebastiany.smartcatalogspl.di.components.activity;



import android.support.v7.app.AppCompatActivity;

import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.di.BaseActivity;
import com.glsebastiany.smartcatalogspl.di.components.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.di.modules.ActivityModule;
import com.glsebastiany.smartcatalogspl.core.presentation.scopes.PerActivity;
import com.glsebastiany.smartcatalogspl.ui.ActivityMain;
import com.glsebastiany.smartcatalogspl.ui.tabbedgallery.ActivityGallery;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {
    AppCompatActivity appCompactActivity();
    BaseAppDisplayFactory baseAppDisplayFactory();

    void inject(BaseActivity baseActivity); // BASE CLASS INJECT ONLY INJECT FIELDS FROM BASE CLASS
    void inject(ActivityGallery activityGallery);
    void inject(ActivityMain activityMain);

}
