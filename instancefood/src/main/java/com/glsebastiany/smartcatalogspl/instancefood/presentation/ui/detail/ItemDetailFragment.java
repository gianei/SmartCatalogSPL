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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.detail;

import android.view.View;
import android.widget.TextView;

import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.RequiresPresenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.ItemDetailFragmentBase;
import com.glsebastiany.smartcatalogspl.instancefood.R;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_gallery_visualization_detail_item_stub)
@RequiresPresenter(ItemDetailPresenter.class)
public class ItemDetailFragment extends ItemDetailFragmentBase<ItemDetailPresenter> {

    public static ItemDetailFragmentBase newInstance(String itemId){
        return ItemDetailFragment_.builder().itemId(itemId).build();
    }


    @Override
    protected void inflateViewStub(ItemModel itemModel) {
        itemDetailStub.setLayoutResource(R.layout.fragment_gallery_visualization_detail_item);
        View newView = itemDetailStub.inflate();
        TextView textView = (TextView) newView.findViewById(R.id.textViewDetailDescription);
        textView.setText(itemModel.getStringId());
    }
}
