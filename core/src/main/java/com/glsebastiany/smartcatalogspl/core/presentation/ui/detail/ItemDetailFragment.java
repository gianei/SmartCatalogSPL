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

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.RequiresPresenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.TouchImageView;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.Utils;

import org.androidannotations.annotations.EFragment;

import java.text.NumberFormat;

@EFragment(resName="fragment_gallery_visualization_detail_item_stub")
@RequiresPresenter(ItemDetailPresenter.class)
public class ItemDetailFragment extends ItemDetailFragmentBase<ItemDetailPresenter, ItemBasicModel>{

    public static ItemDetailFragment newInstance(String itemId){
        return ItemDetailFragment_.builder().itemId(itemId).build();
    }

    @Override
    protected void inflateViewStub(ItemBasicModel itemBasicModel) {
        //TODO
        Context context = getContext();

        itemDetailStub.setLayoutResource(R.layout.fragment_gallery_visualization_detail_item_extended);
        View newView = itemDetailStub.inflate();

        TextView priceText = (TextView) newView.findViewById(R.id.textViewDetailItemPrice);
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        priceText.setText(currencyInstance.format(itemBasicModel.getPrice()));


        TextView idText = (TextView) newView.findViewById(R.id.textViewDetailItemId);
        idText.setText( itemBasicModel.getStringId());

        TextView descriptionText = (TextView) newView.findViewById(R.id.textViewDetailDescription);
        descriptionText.setText(itemBasicModel.getName());

        ImageView buildIcon = (ImageView) newView.findViewById(R.id.item_view_detail_build);
        buildIcon.setVisibility(View.INVISIBLE);

        final TouchImageView image = (TouchImageView) newView.findViewById(R.id.imageViewDetalheItem);
        Glide.with(context).load(Utils.getImageCompleteUrl() + itemBasicModel.getImageUrl())
                .asBitmap()
                .signature(new StringSignature(itemBasicModel.getStringId()))
                .placeholder(ContextCompat.getDrawable(context, R.drawable.image_placeholder))
                .into(image);
    }
}
