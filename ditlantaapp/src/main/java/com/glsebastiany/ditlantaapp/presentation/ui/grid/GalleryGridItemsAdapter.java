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

package com.glsebastiany.ditlantaapp.presentation.ui.grid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.glsebastiany.ditlantaapp.R;
import com.glsebastiany.ditlantaapp.data.ImagesHelper;
import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemComposition;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemPromotedModel;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridCallbacks;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridItemsAdapterBase;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

public class GalleryGridItemsAdapter extends GalleryGridItemsAdapterBase {
    private static final int REGULAR_ITEM_TYPE = 1;
    private static final int PROMOTED_ITEM_TYPE = 2;
    private static final int NEW_ITEM_TYPE = 3;
    private static final int SALE_ITEM_TYPE = 4;

    private static final NumberFormat mCurrencyInstance = NumberFormat.getCurrencyInstance();

    private Context context;

    private List<ItemComposition> items = new LinkedList<>();

    public GalleryGridItemsAdapter(GalleryGridCallbacks galleryGridCallbacks){
        super(galleryGridCallbacks);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int baseItemType) {
        setContextIfNull(parent.getContext());

        View v = LayoutInflater.from(context).inflate(R.layout.view_gallery_card_item, parent, false);

        ViewStub viewStub;
        switch (baseItemType){
            case NEW_ITEM_TYPE:
                viewStub = (ViewStub) v.findViewById(R.id.label_stub);
                viewStub.setLayoutResource(R.layout.image_label_new_scale);
                viewStub.inflate();
                break;
            case SALE_ITEM_TYPE:
                viewStub = (ViewStub) v.findViewById(R.id.label_stub);
                viewStub.setLayoutResource(R.layout.image_label_sale_scale);
                viewStub.inflate();
                break;
            case PROMOTED_ITEM_TYPE:
                viewStub = (ViewStub) v.findViewById(R.id.label_stub);
                viewStub.setLayoutResource(R.layout.image_label_promoted_scale);
                viewStub.inflate();
                break;
        }
        return new BaseItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int baseItemPosition) {
        BaseItemViewHolder baseItemViewHolder = (BaseItemViewHolder) viewHolder;

        ItemBasicModel basicModel = items.get(baseItemPosition).getItemBasicModel();
        ItemPromotedModel promotedModel = items.get(baseItemPosition).getItemPromotedModel();

        baseItemViewHolder.button.setOnClickListener(new GridItemOnClickListener(baseItemPosition));
        baseItemViewHolder.id.setText(basicModel.getStringId());
        baseItemViewHolder.description.setText(basicModel.getName());
        baseItemViewHolder.price.setText(mCurrencyInstance.format(basicModel.getPrice()));
        baseItemViewHolder.buildIcon.setVisibility(promotedModel.getIsAssembled() ? View.VISIBLE : View.INVISIBLE);

        ImagesHelper.loadCardImageWithGlide(context, basicModel, baseItemViewHolder.image);

        if (promotedModel.mustShowPreviousPrice()) {
            baseItemViewHolder.fromPrice.setText(context.getString(
                    R.string.item_view_promoted_price,
                    mCurrencyInstance.format(promotedModel.getPreviousPrice())
            ));
        } else {
            baseItemViewHolder.fromPrice.setText("");
        }
        
        if (viewHolder.getItemViewType() == PROMOTED_ITEM_TYPE)
            baseItemViewHolder.bottomSpecGridLayout.setBackgroundColor(context.getResources().getColor(R.color.primary200));
    }

    @Override
    public int getItemViewType(int baseItemPosition) {
        ItemPromotedModel item = items.get(baseItemPosition).getItemPromotedModel();

        if (item.getIsPromoted())
            return PROMOTED_ITEM_TYPE;

        if (item.getIsSale())
            return SALE_ITEM_TYPE;

        if (item.getIsNew())
            return NEW_ITEM_TYPE;

        return REGULAR_ITEM_TYPE;
    }

    @Override
    public long getItemId(int baseItemPosition) {
        return items.get(baseItemPosition).getItemPromotedModel().getStringId().hashCode();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void setContextIfNull(Context context){
        if (this.context == null)
            this.context = context;
    }

    public int findCategoryPositionInItems(CategoryModel categoryModel){

        for (int i = 0; i < items.size(); i++){
            String itemCategoryId = items.get(i).getItemBasicModel().getCategoryId().toString();
            if (itemCategoryId.startsWith(categoryModel.getStringId())) {
                return i;
            }
        }

        //if nothing found, return -1
        return -1;
    }

    public void addItem(ItemComposition itemComposition) {
        items.add(itemComposition);
        notifyItemInserted(items.size() -1);
    }


    public String[] toStringArray(){
        String[] itemsIds = new String[items.size()];
        for (int i = 0; i < items.size(); i++){
            itemsIds[i] = items.get(i).getItemBasicModel().getStringId();
        }
        return itemsIds;
    }

    private class BaseItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView id;
        public TextView price;
        public TextView description;
        public ImageButton button;
        public TextView fromPrice;
        public ImageView buildIcon;
        public GridLayout bottomSpecGridLayout;

        public BaseItemViewHolder(View v){
            super(v);
            image = (ImageView) v.findViewById(R.id.item_image);
            id = (TextView) v.findViewById(R.id.item_id);
            price = (TextView) v.findViewById(R.id.item_price);
            description = (TextView) v.findViewById(R.id.item_description);
            button = (ImageButton) v.findViewById(R.id.item_view_detail_button);
            fromPrice = (TextView) v.findViewById(R.id.item_price_previous);
            buildIcon = (ImageView) v.findViewById(R.id.item_view_detail_build);
            bottomSpecGridLayout = (GridLayout) v.findViewById(R.id.bottom_spec_layout);
        }
    }

    private class GridItemOnClickListener implements View.OnClickListener, View.OnLongClickListener{
        private int position;

        public GridItemOnClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {

            galleryGridCallbacks.switchToItemView( position);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }


}