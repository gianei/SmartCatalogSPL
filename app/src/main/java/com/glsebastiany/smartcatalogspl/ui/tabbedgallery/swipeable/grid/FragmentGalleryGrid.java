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

package com.glsebastiany.smartcatalogspl.ui.tabbedgallery.swipeable.grid;

import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.di.BaseFragment;
import com.glsebastiany.smartcatalogspl.di.components.ItemsGroupComponent;
import com.glsebastiany.smartcatalogspl.di.helper.HasComponent;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseSwipeableController;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import rx.Observable;

@EFragment(R.layout.fragment_gallery_visualization_grid)
public class FragmentGalleryGrid extends BaseFragment implements HasComponent<ItemsGroupComponent>{

    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;

    @ViewById(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    BaseSwipeableController galleryGridController;

    @Inject
    Observable<ItemModel> itemModelObservable;

    @Override
    protected void initializeInjector() {
        getComponent().inject(this);
    }

    @AfterViews
    public void afterViews() {
        setupRecyclerView();
    }

    private void setupRecyclerView(){
        galleryGridController.setupRecyclerView(getActivity(), itemModelObservable, progressBar, recyclerView, getFragmentManager());

    }

    @Override
    public ItemsGroupComponent getComponent() {
        return ((HasComponent<ItemsGroupComponent>) getParentFragment()).getComponent();
    }
}
