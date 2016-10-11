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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.itempager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.di.InjectableActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EActivity(resName="activity_gallery_visualization_pager")
public abstract class ItemPagerActivityBase extends InjectableActivity {

    @Inject
    BaseAppDisplayFactory appDisplayFactory;

    @Extra
    public int itemPosition;

    @Extra
    @InstanceState
    public String[] categoriesIds;

    @ViewById(resName="pager")
    public ViewPager mPager;

    @ViewById(resName="pager_toolbar")
    public Toolbar toolbar;

    MyPageAdapter mPagerAdapter;

    @AfterViews
    public void afterViews() {
        mPagerAdapter = new MyPageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(itemPosition);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class MyPageAdapter extends FragmentStatePagerAdapter {

        private MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return appDisplayFactory.getItemDetailFragment(categoriesIds[position]);
        }

        @Override
        public int getCount() {
            return categoriesIds.length;
        }
    }

    @Override
    protected void injectApplicationComponent() {
        injectMe(this);
    }

    protected abstract void injectMe(ItemPagerActivityBase itemPagerActivityBase);

}
