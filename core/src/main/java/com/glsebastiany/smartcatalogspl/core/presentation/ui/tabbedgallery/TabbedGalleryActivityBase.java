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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.di.InjectableActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

import static com.glsebastiany.smartcatalogspl.core.presentation.ui.Utils.depthFirstOnBackPressed;

@EActivity(resName="activity_gallery")
public abstract class TabbedGalleryActivityBase extends InjectableActivity {

    @Inject
    protected ItemUseCases itemUseCases;

    @Inject
    protected CategoryUseCases categoryUseCases;

    @Inject
    protected BaseAppDisplayFactory appDisplayFactory;

    @Extra
    public String[] categoriesIds;

    @InstanceState
    public boolean isFromSavedInstance = false;

    @ViewById(resName="main_toolbar")
    public Toolbar toolbar;

    protected long lastPress;
    private Toast toast;

    @AfterViews
    protected void afterViews() {
        setupGalleryFragment();
    }

    private void setupGalleryFragment() {

        if (!isFromSavedInstance) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Fragment galleryFragment = appDisplayFactory.provideGalleryFragment(categoriesIds);
            fragmentTransaction.add(R.id.main_fragment_container, galleryFragment);
            fragmentTransaction.commit();

            isFromSavedInstance = true;
        }

    }

    @Override
    public void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (depthFirstOnBackPressed(getSupportFragmentManager())) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        //Toast.LENGTH_SHORT is 2000
        if(currentTime - lastPress > 2000){
            toast = Toast.makeText(getBaseContext(), R.string.confirm_back_toast, Toast.LENGTH_SHORT);
            toast.show();
            lastPress = currentTime;
        }else{
            if (toast != null){
                toast.cancel();
            }
            super.onBackPressed();
        }
    }

    @Override
    protected void injectApplicationComponent() {
        injectMe(this);
    }

    protected abstract void injectMe(TabbedGalleryActivityBase tabbedGalleryBaseActivity);
}
