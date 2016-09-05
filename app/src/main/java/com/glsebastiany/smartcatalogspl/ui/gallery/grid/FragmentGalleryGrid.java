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

package com.glsebastiany.smartcatalogspl.ui.gallery.grid;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.di.BaseFragment;
import com.glsebastiany.smartcatalogspl.presentation.DisplayFactory;
import com.glsebastiany.smartcatalogspl.presentation.controller.BaseGalleryGridController;
import com.glsebastiany.smartcatalogspl.presentation.widget.SpacesItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(R.layout.fragment_gallery_visualization_grid)
public class FragmentGalleryGrid extends BaseFragment {

    private static final String CATEGORY = "category";

    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;

    @ViewById(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    BaseGalleryGridController galleryGridController;

    public static FragmentGalleryGrid newInstance(String category) {
        return FragmentGalleryGrid_.builder().arg(CATEGORY, category).build();
    }



    @Override
    protected void initializeInjector() {
        getAndroidApplication().getApplicationComponent().inject(this);

    }

    @AfterViews
    public void afterViews() {
        setupRecyclerView();
    }

    private void setupRecyclerView(){
        String category = getArguments().getString(CATEGORY);
        galleryGridController.setupRecyclerView(getActivity(), category, progressBar, recyclerView);

    }

}
