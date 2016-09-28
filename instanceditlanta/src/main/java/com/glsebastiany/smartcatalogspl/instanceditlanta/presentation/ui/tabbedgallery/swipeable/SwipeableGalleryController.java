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

package com.glsebastiany.smartcatalogspl.instanceditlanta.presentation.ui.tabbedgallery.swipeable;

import android.content.Context;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.controller.BaseSwipeableGalleryController;
import com.glsebastiany.smartcatalogspl.core.presentation.widget.TouchImageView;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.ImagesHelper;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.Item;

import java.text.NumberFormat;

import javax.inject.Inject;

import rx.Observable;

public class SwipeableGalleryController extends BaseSwipeableGalleryController {

    @Inject
    Context context;

    @Inject
    ItemUseCases itemUseCases;

    @Inject
    BaseAppDisplayFactory baseAppDisplayFactory;

    @Inject
    public SwipeableGalleryController(){}

    public Observable<ItemModel> getItemsObservableInternal(String categoryId){
        return itemUseCases.allFromCategory(categoryId);
    }

    @Override
    public void inflateItemDetailStub(ViewStub viewStub, ItemModel itemModel){
        viewStub.setLayoutResource(R.layout.fragment_gallery_visualization_detail_item);
        View newView = viewStub.inflate();

        Item item = (Item) itemModel;

        ViewStub labelViewStub =  (ViewStub) newView.findViewById(R.id.label_stub);
        inflateLabelViewStub(labelViewStub, item);

        TextView priceText = (TextView) newView.findViewById(R.id.textViewDetailItemPrice);
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        priceText.setText(currencyInstance.format(item.getPrice()));

        TextView fromText = (TextView) newView.findViewById(R.id.textViewDetailItemPricePrevious);
        if (item.mustShowPreviousPrice()) {
            fromText.setText(context.getString(
                    R.string.item_view_promoted_price,
                    currencyInstance.format(item.getPreviousPrice())
            ));
        } else {
            fromText.setText("");
        }

        TextView idText = (TextView) newView.findViewById(R.id.textViewDetailItemId);
        idText.setText(item.getId().toString());

        TextView descriptionText = (TextView) newView.findViewById(R.id.textViewDetailDescription);
        descriptionText.setText(item.getName());

        ImageView buildIcon = (ImageView) newView.findViewById(R.id.item_view_detail_build);
        buildIcon.setVisibility(item.getIsAssembled() ? View.VISIBLE : View.INVISIBLE);

        final TouchImageView image = (TouchImageView) newView.findViewById(R.id.imageViewDetalheItem);
        ImagesHelper.loadDetailImageWithGlide(context, item, image);

    }

    private void inflateLabelViewStub(ViewStub labelViewStub, Item baseItem) {
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
