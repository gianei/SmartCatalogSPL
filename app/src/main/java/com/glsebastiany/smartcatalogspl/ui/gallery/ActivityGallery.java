package com.glsebastiany.smartcatalogspl.ui.gallery;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.di.BaseActivity;
import com.glsebastiany.smartcatalogspl.di.components.GalleryComponent;
import com.glsebastiany.smartcatalogspl.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.ui.gallery.grid.GalleryGridFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

@EActivity(R.layout.activity_gallery)
public class ActivityGallery extends BaseActivity {

    @InstanceState
    boolean fromSavedInstance = false;

    private GalleryComponent galleryComponent;

    private Fragment galleryFragment;

    private long lastPress;
    private Toast toast;

    @Inject
    ItemUseCases itemUseCases;

    @ViewById(R.id.main_toolbar)
    Toolbar toolbar;

    @AfterViews
    protected void afterViews() {

        setupToolbar();
        setupGalleryFragment();

    }


    private void setupToolbar() {
        setupToolbarLogo(toolbar);
        setSupportActionBar(toolbar);
        //setupToolbarNavigation();
    }

    private void setupToolbarLogo(Toolbar toolbar) {
        //toolbar.setLogo(R.drawable.image_logo);
        toolbar.setLogoDescription(getString(R.string.app_name));
        toolbar.setTitle(getString(R.string.app_name));
    }

    private void setupToolbarNavigation() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }


    private void setupGalleryFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //galleryFragment = GalleryFragment_.builder().build();
        galleryFragment = GalleryGridFragment_.builder().build();
        fragmentTransaction.add(R.id.main_fragment_container, galleryFragment, GalleryFragment_.TAG);
        fragmentTransaction.commit();

    }

    @Override
    public void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }


    /**
     * Workaround for child fragments backstack
        based on http://stackoverflow.com/a/24176614
     * @param fragmentManager
     * @return true when backStack is popped
     */
    private boolean depthFirstOnBackPressed(FragmentManager fragmentManager) {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if (fragmentList != null && fragmentList.size() > 0) {
            for (Fragment fragment : fragmentList) {
                if (fragment == null) {
                    continue;
                }
                if (fragment.isVisible()) {
                    if (fragment.getChildFragmentManager() != null) {
                        if (depthFirstOnBackPressed(fragment.getChildFragmentManager())) {
                            return true;
                        }
                    }
                }
            }
        }

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (depthFirstOnBackPressed(fm)) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        //Toast.LENGTH_SHORT is 2000
        if(currentTime - lastPress > 2000){
            toast = Toast.makeText(getBaseContext(), "Back sure?", Toast.LENGTH_SHORT);
            toast.show();
            lastPress = currentTime;
        }else{
            if (toast != null){
                toast.cancel();
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void initializeInjector() {
        getApplicationComponent().inject(this);

        if (!fromSavedInstance){
            getAndroidApplication().createGalleryComponent(itemUseCases.allItems());
            fromSavedInstance = true;
        }

    }

}