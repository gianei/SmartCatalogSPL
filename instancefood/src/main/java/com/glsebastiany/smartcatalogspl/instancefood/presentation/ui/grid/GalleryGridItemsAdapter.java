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

package com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.grid;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridCallbacks;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.grid.GalleryGridItemsAdapterBase;
import com.glsebastiany.smartcatalogspl.instancefood.R;
import com.glsebastiany.smartcatalogspl.instancefood.presentation.ui.main.MainAdapter;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

import rx.Observable;

public class GalleryGridItemsAdapter extends GalleryGridItemsAdapterBase{
    private static final int REGULAR_ITEM_TYPE = 1;

    private Context context;

    private List<ItemModel> items = new LinkedList<>();

    public GalleryGridItemsAdapter(GalleryGridCallbacks galleryGridCallbacks){
        super(galleryGridCallbacks);
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

        ItemModel item = items.get(baseItemPosition);

        baseItemViewHolder.button.setOnClickListener(new GridItemOnClickListener(baseItemPosition));
        baseItemViewHolder.description.setText(item.getStringId());

        Glide.with(context).load("https://unsplash.it/300?random")
                .asBitmap()
                .signature(new StringSignature(item.getStringId()))
                .placeholder(ContextCompat.getDrawable(context, R.drawable.image_placeholder))
                .into(baseItemViewHolder.image);
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
    public void addItem(ItemModel itemModel) {
        items.add(itemModel);
        notifyItemInserted(items.size() - 1);

    }

    public int findCategoryPositionInItems(CategoryModel categoryModel){
        //if nothing found, return top position
        return 0;
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
        public TextView description;
        public ImageButton button;

        public BaseItemViewHolder(View v){
            super(v);
            image = (ImageView) v.findViewById(R.id.item_image);
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
            galleryGridCallbacks.switchToItemView(position);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

}