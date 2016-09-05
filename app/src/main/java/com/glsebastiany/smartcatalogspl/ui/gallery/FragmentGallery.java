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

package com.glsebastiany.smartcatalogspl.ui.gallery;

import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.di.BaseFragment;
import com.glsebastiany.smartcatalogspl.domain.CategoryUseCases;
import com.glsebastiany.smartcatalogspl.presentation.DisplayFactory;
import com.glsebastiany.smartcatalogspl.presentation.FragmentDisplayFactory;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(R.layout.fragment_gallery)
public class FragmentGallery extends BaseFragment {

    public static final String TAG = "galleryFragment";

    @Inject
    DisplayFactory displayFactory;

    @Inject
    CategoryUseCases categoryUseCases;

    @Inject
    FragmentDisplayFactory fragmentDisplayFactory;

    @Click(R.id.drawerTriggerView)
    public void onDrawerTriggerClick(){
        if (drawerLayout.isDrawerOpen(drawerListView))
            drawerLayout.closeDrawer(drawerListView);
        else
            drawerLayout.openDrawer(drawerListView);
    }

    @ViewById(R.id.pager)
    ViewPager viewPager;

    @ViewById(R.id.progressBar)
    ProgressBar progressBar;

    @ViewById(R.id.slidingTabLayout)
    TabLayout tabLayout;

    @ViewById(R.id.left_drawer)
    ListView drawerListView;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @Override
    protected void initializeInjector() {
        getFragmentComponent().inject(this);

        /*GalleryCategoriesComponent galleryCategoriesComponent = DaggerGalleryCategoriesComponent.builder()
                .applicationComponent(getApplicationComponent())
                .fragmentModule(getFragmentModule())
                .pageAdapterModule(new PageAdapterModule())
                .build();

        galleryCategoriesComponent.inject(this);*/
    }

    @AfterViews
    public void afterViews() {
        setHasOptionsMenu(true);
        setupSlidingTabsAndViewPager();
        setupDrawer();

    }


    private void setupSlidingTabsAndViewPager() {
        setupViewPager();

        setupSlidingTabs();
    }

    private void setupViewPager() {
        setupViewPagerAdapter();

        viewPager.setAdapter(fragmentDisplayFactory.categoriesPagerAdapter());

        progressBar.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);

        //add listener to change drawer parent category
        //viewPager.addOnPageChangeListener(new OnCategoryPageChangeListener(categoryItemsViewPagerAdapter));
    }

    private void setupViewPagerAdapter() {

        //categoryItemsViewPagerAdapter =  new CategoryItemsViewPagerAdapter(getChildFragmentManager());


    }


    private void setupSlidingTabs() {
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void setupDrawer() {

        drawerListView.setAdapter(displayFactory.drawerAdapter());


    };



    //categoryItemsViewPagerAdapter.registerDataSetObserver(dataSetObserver);

        //if (!drawerListView.hasOnClickListeners())
        //    drawerListView.setOnItemClickListener(new DrawerItemClickListener());


    /*private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            GalleryVisualizationFragment galleryVisualizationFragment = categoryItemsViewPagerAdapter.getRegisteredFragment(viewPager.getCurrentItem());

            if (drawerLayout.isDrawerOpen(drawerListView))
                drawerLayout.closeDrawer(drawerListView);


            BaseCategory categoryItem = drawerListViewAdapter.getCategoryItem(position);
            if (categoryItem.getChildren().size() > 0)
                // use first sub category if available
                categoryItem = categoryItem.getChildren().get(0);

            galleryVisualizationFragment.moveToSubCategorySection(categoryItem);
        }
    }*/

    /*private class OnCategoryPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        CategoryItemsViewPagerAdapter categoryItemsViewPagerAdapter;

        public OnCategoryPageChangeListener(CategoryItemsViewPagerAdapter categoryItemsViewPagerAdapter) {
            this.categoryItemsViewPagerAdapter = categoryItemsViewPagerAdapter;
        }

        @Override
        public void onPageSelected(int position) {
            long catKey = CompatHelper.parseLong(categoryItemsViewPagerAdapter.get(position).getKey());
            if (drawerListViewAdapter != null) {
                BaseCategory baseCategory = (BaseCategory) baseCategoryRepository.get(catKey);
                drawerListViewAdapter.changeParentItem(categoryUseCases, baseCategory);
                drawerListViewAdapter.notifyDataSetChanged();
            } else {
                ApplicationComponent applicationComponent = ((ApplicationInjectedAppCompatActivity)getActivity()).getApplicationComponent();
                BaseCategory firstCategory = FirebaseInterpreter.getBaseCategory(categoryItemsViewPagerAdapter.get(position), applicationComponent);
                if (firstCategory != null) {
                    drawerListViewAdapter = new DrawerListViewAdapter(getActivity(), categoryUseCases, firstCategory);
                    drawerListView.setAdapter(drawerListViewAdapter);


                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }*/

}
