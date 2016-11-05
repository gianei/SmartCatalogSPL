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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.main.basic;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.data.categorygroup.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.RequiresPresenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.main.MainActivityBase;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends MainActivityBase {

    public RecyclerView recyclerView;

    protected MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        afterViews();
    }

    public void afterViews(){
        setSupportActionBar(toolbar);

        mainAdapter = getAdapter();

        recyclerView.setLayoutManager(
                new GridLayoutManager(
                        MainActivity.this,
                        MainActivity.this.getResources().getInteger(com.glsebastiany.smartcatalogspl.core.R.integer.grid_span_start),
                        LinearLayoutManager.VERTICAL, false
                )
        );

        recyclerView.setAdapter(mainAdapter);

        presenterAfterView();

        setupToolbar();
    }

    public void addItem(CategoryGroupModel suitCase) {
        mainAdapter.addItem(suitCase);
    }

    public void stopLoading() {
        super.stopLoading();
        recyclerView.setVisibility(View.VISIBLE);
    }


    public MainAdapter getAdapter(){
        return new MainAdapter(this, baseAppDisplayFactory);
    }

}
