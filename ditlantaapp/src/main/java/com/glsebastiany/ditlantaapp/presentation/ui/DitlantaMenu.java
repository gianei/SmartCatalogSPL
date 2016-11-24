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

package com.glsebastiany.ditlantaapp.presentation.ui;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.glsebastiany.ditlantaapp.R;
import com.glsebastiany.ditlantaapp.data.imagefetching.ImageFetcherIntentService;
import com.glsebastiany.ditlantaapp.data.preferences.ActivityPreferences_;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.Utils;

import javax.inject.Inject;

public class DitlantaMenu implements BaseAppDisplayFactory.MenuConfigurator {

    @Inject
    public DitlantaMenu(){}

    @Override
    public boolean inflateMenus(AppCompatActivity activity, Menu menu) {
        MenuInflater menuInflater = activity.getMenuInflater();
        menuInflater.inflate(R.menu.menu_pin, menu);
        menuInflater.inflate(R.menu.menu_updates, menu);
        return true;
    }

    @Override
    public boolean menuSelected(AppCompatActivity activity, MenuItem menuItem) {
        int itemId_ = menuItem.getItemId();
        if (itemId_ == com.glsebastiany.ditlantaapp.R.id.menu_update_images) {
            ImageFetcherIntentService.startService(activity, ImageFetcherIntentService.FetchType.CLOUDINARY);
            return true;
        }
        if (itemId_ == com.glsebastiany.ditlantaapp.R.id.menu_update_images_locally) {
            ImageFetcherIntentService.startService(activity, ImageFetcherIntentService.FetchType.LOCAL_LAN);
            return true;
        }
        if (itemId_ == com.glsebastiany.ditlantaapp.R.id.menu_settings) {
            ActivityPreferences_.intent(activity).start();
            return true;
        }
        if (itemId_ == com.glsebastiany.ditlantaapp.R.id.menu_switch_lock_task) {
            Utils.switchLockTasMode(activity);
            return true;
        }
        return false;
    }
}
