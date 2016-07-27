package com.glsebastiany.smartcatalogspl.ui.gallery.grid;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.di.BaseFragment;
import com.glsebastiany.smartcatalogspl.presentation.widget.SpacesItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(R.layout.fragment_gallery_visualization_grid)
public class FragmentGalleryGrid extends BaseFragment {

    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;

    @Inject
    RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerViewAdapter;

    @AfterViews
    public void afterViews() {
        getAndroidApplication().getGalleryComponent().inject(this);
        setupRecyclerView();
    }

    private void setupRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addItemDecoration(
                new SpacesItemDecoration(getContext().getResources().getDimensionPixelSize(R.dimen.grid_cards_spacing)));
    }
}
