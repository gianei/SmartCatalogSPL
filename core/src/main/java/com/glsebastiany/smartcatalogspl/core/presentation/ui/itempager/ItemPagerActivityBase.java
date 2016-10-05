package com.glsebastiany.smartcatalogspl.core.presentation.ui.itempager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

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

    MyPageAdapter mPagerAdapter;

    @AfterViews
    public void afterViews() {
        mPagerAdapter = new MyPageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(itemPosition);
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
