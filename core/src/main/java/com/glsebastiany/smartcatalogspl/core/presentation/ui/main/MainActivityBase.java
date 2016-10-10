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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.main;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.data.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.nucleous.MvpRxActivityBase;
import com.glsebastiany.smartcatalogspl.core.nucleous.Presenter;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EActivity(resName="activity_main")
public abstract class MainActivityBase<P extends Presenter> extends MvpRxActivityBase<P> {

    @ViewById(resName="progressBar")
    public ProgressBar progressBar;

    @ViewById(resName="recycler_view")
    public RecyclerView recyclerView;

    @ViewById(resName="toolbar")
    public Toolbar toolbar;

    @Inject
    public BaseAppDisplayFactory baseAppDisplayFactory;

    protected MainAdapterBase mainAdapter;

    @AfterViews
    public void afterViews(){
        setSupportActionBar(toolbar);

        mainAdapter = getAdapter();

        recyclerView.setLayoutManager(
                new GridLayoutManager(
                        MainActivityBase.this,
                        MainActivityBase.this.getResources().getInteger(com.glsebastiany.smartcatalogspl.core.R.integer.grid_span_start),
                        LinearLayoutManager.VERTICAL, false
                )
        );

        recyclerView.setAdapter(mainAdapter);
    }


    @Override
    protected void injectApplicationComponent() {
        injectMe(this);
    }

    protected abstract void injectMe(MainActivityBase<P> mainActivityBase);

    public void stopLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void addItem(CategoryGroupModel suitCase) {
        mainAdapter.addItem(suitCase);
    }


    /**
     * Should return an adapter created onCreate
     * @return
     */
    public abstract MainAdapterBase getAdapter();

    public void clear(){
        mainAdapter.clear();
    }
}
