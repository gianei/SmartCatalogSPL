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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.RequiresPresenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.ItemDetailFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.TouchImageView;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.ImagesHelper;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.Item;

import org.androidannotations.annotations.EFragment;

import java.text.NumberFormat;

@EFragment(R.layout.fragment_gallery_visualization_detail_item_stub)
@RequiresPresenter(ItemDetailPresenter.class)
public class ItemDetailFragment extends ItemDetailFragmentBase<ItemDetailPresenter> {

    public static ItemDetailFragmentBase newInstance(String itemId){
        return ItemDetailFragment_.builder().itemId(itemId).build();
    }

    @Override
    protected void inflateViewStub(ItemModel itemModel) {
        Context context = getContext();
        itemDetailStub.setLayoutResource(R.layout.fragment_gallery_visualization_detail_item);
        View newView = itemDetailStub.inflate();

        ViewStub labelViewStub =  (ViewStub) newView.findViewById(R.id.label_stub);
        inflateLabelViewStub(labelViewStub, (Item) itemModel);

        TextView priceText = (TextView) newView.findViewById(R.id.textViewDetailItemPrice);
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        priceText.setText(currencyInstance.format(((Item) itemModel).getPrice()));

        TextView fromText = (TextView) newView.findViewById(R.id.textViewDetailItemPricePrevious);
        if (((Item) itemModel).mustShowPreviousPrice()) {
            fromText.setText(context.getString(
                    R.string.item_view_promoted_price,
                    currencyInstance.format(((Item) itemModel).getPreviousPrice())
            ));
        } else {
            fromText.setText("");
        }

        TextView idText = (TextView) newView.findViewById(R.id.textViewDetailItemId);
        idText.setText(((Item) itemModel).getId().toString());

        TextView descriptionText = (TextView) newView.findViewById(R.id.textViewDetailDescription);
        descriptionText.setText(((Item) itemModel).getName());

        ImageView buildIcon = (ImageView) newView.findViewById(R.id.item_view_detail_build);
        buildIcon.setVisibility(((Item) itemModel).getIsAssembled() ? View.VISIBLE : View.INVISIBLE);

        final TouchImageView image = (TouchImageView) newView.findViewById(R.id.imageViewDetalheItem);
        ImagesHelper.loadDetailImageWithGlide(context, (Item) itemModel, image);

    }

    private static void inflateLabelViewStub(ViewStub labelViewStub, Item baseItem) {
        if (baseItem.getIsNew()){
            labelViewStub.setLayoutResource(R.layout.image_label_new);
            labelViewStub.inflate();
        } else
        if (baseItem.getIsSale()){
            labelViewStub.setLayoutResource(R.layout.image_label_sale);
            labelViewStub.inflate();
        } else
        if (baseItem.getIsPromoted()){
            labelViewStub.setLayoutResource(R.layout.image_label_promoted);
            labelViewStub.inflate();
        }
    }
}
