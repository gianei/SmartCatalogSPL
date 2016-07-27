package com.glsebastiany.smartcatalogspl.ui.gallery.grid;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.glsebastiany.smartcatalogspl.AndroidApplication;
import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.di.ApplicationComponent;
import com.glsebastiany.smartcatalogspl.presentation.DisplayFactory;
import com.glsebastiany.smartcatalogspl.presentation.widget.SpacesItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(R.layout.fragment_gallery_visualization_grid)
public class GalleryGridFragment extends Fragment {


    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;

    @Inject
    DisplayFactory displayFactory;

    @AfterViews
    public void afterViews() {
        ApplicationComponent applicationComponent = ((AndroidApplication) getActivity().getApplication()).getApplicationComponent();

        applicationComponent.inject(this);
        setupRecyclerView();
    }


    private void setupRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setAdapter(displayFactory.gritItemsAdapter());


        recyclerView.addItemDecoration(
                new SpacesItemDecoration(getContext().getResources().getDimensionPixelSize(R.dimen.grid_cards_spacing)));
    }

}
