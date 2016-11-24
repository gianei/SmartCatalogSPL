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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.detail;

import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;

import com.glsebastiany.smartcatalogspl.core.data.item.ItemId;
import com.glsebastiany.smartcatalogspl.core.presentation.mvpFramework.MvpRxFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.mvpFramework.Presenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

@EFragment(resName="fragment_gallery_visualization_detail_item_stub")
public abstract class ItemDetailFragmentBase<P extends Presenter, I extends ItemId> extends MvpRxFragmentBase<P> {

    @ViewById(resName="item_detail_stub")
    public ViewStub itemDetailStub;

    @ViewById(resName="progressBar")
    public ProgressBar progressBar;

    @FragmentArg
    @InstanceState
    public String itemId;

    @AfterViews
    protected void afterViews(){
        presenterAfterViews();
    }

    public void addItem(I item){

        inflateViewStub(item);

        progressBar.setVisibility(View.GONE);
        itemDetailStub.setVisibility(View.VISIBLE);
    }

    protected abstract void inflateViewStub(I item);

}
