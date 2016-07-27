package com.glsebastiany.smartcatalogspl.ui.gallery;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.glsebastiany.ditlantaapp.ui.suitCase.SuitCase;
import com.google.firebase.database.DatabaseReference;
import com.glsebastiany.data.ditlanta.firebase.FirebaseSuitCaseCategory;
import com.glsebastiany.ditlantaapp.domain.FirebaseInterpreter;
import com.glsebastiany.ditlantaapp.di.components.ApplicationComponent;
import com.glsebastiany.ditlantaapp.ui.gallery.visualization.GalleryVisualizationFragment;
import com.glsebastiany.ditlantaapp.ui.gallery.widget.ZoomableGridLayoutManager;
import com.glsebastiany.ditlantaapp.ui.utils.FirebaseArray;

import static com.glsebastiany.ditlantaapp.ui.gallery.visualization.GalleryVisualizationFragment.newInstance;

public class CategoryItemsViewPagerAdapter extends FragmentStatePagerAdapter {

    private final ApplicationComponent applicationComponent;
    SuitCase suitCase;
    SparseArray<GalleryVisualizationFragment> registeredFragments = new SparseArray<>();

    public CategoryItemsViewPagerAdapter(FragmentManager fm, int suitCaseId, ApplicationComponent applicationComponent) {
        super(fm);
        this.applicationComponent = applicationComponent;

        this.suitCase = SuitCase.suitCasesList.get(suitCaseId);

        /*// listen and propagates events from DatabaseReference
        this.categoryUiItems.setOnChangedListener(new FirebaseArray.OnChangedListener() {
            public void onChanged(EventType type, int index, int oldIndex) {
                CategoryItemsViewPagerAdapter.this.notifyDataSetChanged();
            }
        });*/

    }

    //public void cleanup() {
    //    this.categoryUiItems.cleanup();
    //}

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        GalleryVisualizationFragment galleryVisualizationFragment
                = newInstance(get(position));

        return galleryVisualizationFragment;
    }

    public FirebaseSuitCaseCategory get(int position){
        FirebaseSuitCaseCategory firebaseSuitCaseCategory = new FirebaseSuitCaseCategory(suitCase.getCategoriesId().get(position).toString());

        return firebaseSuitCaseCategory;
    }

    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return FirebaseInterpreter.getCategoryName(get(position), applicationComponent);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        GalleryVisualizationFragment fragment = (GalleryVisualizationFragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public GalleryVisualizationFragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    /*public int getCategoryPosition(long baseCategoryId){
        for(int i = 0; i < categoryUiItems.getCount(); i++){
            if (get(i).getId() == baseCategoryId)
                return i;
        }
        return -1;
    }*/

    // This method return the Number of slidingTabLayout for the slidingTabLayout Strip

    @Override
    public int getCount() {
        return suitCase.getCategoriesId().size();
    }


    public int zoomAllFragments(ZoomableGridLayoutManager.ZoomType zoomType){
        int currentZoom = 0;
        for (int i = 0; i < getCount(); i++){
            GalleryVisualizationFragment vis = getRegisteredFragment(i);
            if (vis != null) {
                currentZoom = vis.zoom(zoomType);
            }

        }
        if (currentZoom == 0){
            throw new Error("will never happen");
        }

        return currentZoom;
    }


}