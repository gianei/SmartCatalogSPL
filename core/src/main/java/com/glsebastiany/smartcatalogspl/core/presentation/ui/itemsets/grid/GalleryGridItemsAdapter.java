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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.itemsets.grid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.Singletons;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.itemsets.ItemSetsCallbacks;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

public class GalleryGridItemsAdapter extends GalleryGridItemsAdapterBase<ItemBasicModel> {
    private static final int REGULAR_ITEM_TYPE = 1;

    private Context context;

    private List<ItemBasicModel> items = new LinkedList<>();

    public GalleryGridItemsAdapter(ItemSetsCallbacks itemSetsCallbacks){
        super(itemSetsCallbacks);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int baseItemType) {
        setContextIfNull(parent.getContext());

        View v = LayoutInflater.from(context).inflate(R.layout.view_gallery_card_item, parent, false);

        return new BaseItemViewHolder(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int baseItemPosition) {
        BaseItemViewHolder baseItemViewHolder = (BaseItemViewHolder) viewHolder;

        ItemBasicModel item = items.get(baseItemPosition);

        baseItemViewHolder.button.setOnClickListener(new GridItemOnClickListener(baseItemPosition));


        baseItemViewHolder.itemId.setText(item.getStringId());
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        baseItemViewHolder.itemPrice.setText(currencyInstance.format(item.getPrice()));
        baseItemViewHolder.description.setText(item.getName());

        Singletons.getInstance().baseAppDisplayFactory
                .loadCardImageWithGlide(context, item, baseItemViewHolder.image);
    }

    @Override
    public int getItemViewType(int baseItemPosition) {
        return REGULAR_ITEM_TYPE;
    }

    @Override
    public long getItemId(int baseItemPosition) {
        return items.get(baseItemPosition).getStringId().hashCode();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void setContextIfNull(Context context){
        if (this.context == null)
            this.context = context;
    }

    @Override
    public void addItem(ItemBasicModel item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);

    }

    public int findCategoryPositionInItems(CategoryModel categoryModel){
        //if nothing found, return top position
        return -1;
    }

    @Override
    public String[] toStringArray() {
        String[] itemsIds = new String[items.size()];
        for (int i = 0; i < items.size(); i++){
            itemsIds[i] = items.get(i).getStringId();
        }
        return itemsIds;
    }

    private class BaseItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView itemId;
        public TextView itemPrice;
        public TextView description;
        public ImageButton button;

        public BaseItemViewHolder(View v){
            super(v);
            image = (ImageView) v.findViewById(R.id.item_image);
            itemId = (TextView) v.findViewById(R.id.item_id);
            itemPrice = (TextView) v.findViewById(R.id.item_price);
            description = (TextView) v.findViewById(R.id.item_description);
            button = (ImageButton) v.findViewById(R.id.item_view_detail_button);
        }
    }

    private class GridItemOnClickListener implements View.OnClickListener, View.OnLongClickListener{
        private int position;

        public GridItemOnClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            itemSetsCallbacks.switchToItemView(position);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

}