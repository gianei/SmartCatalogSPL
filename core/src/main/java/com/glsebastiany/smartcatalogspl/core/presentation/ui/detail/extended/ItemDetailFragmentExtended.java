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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.extended;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemExtendedModel;
import com.glsebastiany.smartcatalogspl.core.presentation.nucleous.RequiresPresenter;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.Singletons;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.detail.ItemDetailFragmentBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.TouchImageView;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.Utils;

import org.androidannotations.annotations.EFragment;

import java.text.NumberFormat;

@EFragment(resName="fragment_gallery_visualization_detail_item_stub")
@RequiresPresenter(ItemDetailPresenterExtended.class)
public class ItemDetailFragmentExtended extends ItemDetailFragmentBase<ItemDetailPresenterExtended, ItemExtendedModel> {

    public static ItemDetailFragmentExtended newInstance(String itemId){
        return ItemDetailFragmentExtended_.builder().itemId(itemId).build();
    }

    @Override
    protected void inflateViewStub(ItemExtendedModel itemExtendedModel) {
        Context context = getContext();

        ItemBasicModel itemBasicModel = itemExtendedModel.getItemBasicModel();

        itemDetailStub.setLayoutResource(R.layout.fragment_gallery_visualization_detail_item_extended);
        View newView = itemDetailStub.inflate();

        ViewStub labelViewStub =  (ViewStub) newView.findViewById(R.id.label_stub);
        inflateLabelViewStub(labelViewStub, itemExtendedModel);

        TextView priceText = (TextView) newView.findViewById(R.id.textViewDetailItemPrice);
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        priceText.setText(currencyInstance.format(itemBasicModel.getPrice()));

        TextView fromText = (TextView) newView.findViewById(R.id.textViewDetailItemPricePrevious);
        if (itemExtendedModel.mustShowPreviousPrice()) {
            fromText.setText(context.getString(
                    R.string.item_view_promoted_price,
                    currencyInstance.format(itemExtendedModel.getPreviousPrice())
            ));
        } else {
            fromText.setText("");
        }

        TextView idText = (TextView) newView.findViewById(R.id.textViewDetailItemId);
        idText.setText( itemBasicModel.getStringId());

        TextView descriptionText = (TextView) newView.findViewById(R.id.textViewDetailDescription);
        descriptionText.setText(itemBasicModel.getName());

        ImageView buildIcon = (ImageView) newView.findViewById(R.id.item_view_detail_build);
        buildIcon.setVisibility(itemExtendedModel.getIsAssembled() ? View.VISIBLE : View.INVISIBLE);

        final TouchImageView image = (TouchImageView) newView.findViewById(R.id.imageViewDetalheItem);

        Singletons.getInstance().imagesHelperBase.loadDetailImageWithGlide(getContext(), itemBasicModel, image);

    }

    private static void inflateLabelViewStub(ViewStub labelViewStub, ItemExtendedModel baseItem) {
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
