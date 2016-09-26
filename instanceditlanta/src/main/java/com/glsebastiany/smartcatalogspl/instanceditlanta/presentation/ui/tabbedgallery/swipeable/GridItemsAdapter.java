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
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.glsebastiany.smartcatalogspl.core.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.ImagesHelper;
import com.glsebastiany.smartcatalogspl.instanceditlanta.data.db.Item;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

import rx.Observable;

public class GridItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements rx.Observer<ItemModel>{
    private static final int REGULAR_ITEM_TYPE = 1;
    private static final int PROMOTED_ITEM_TYPE = 2;
    private static final int NEW_ITEM_TYPE = 3;
    private static final int SALE_ITEM_TYPE = 4;

    private static final NumberFormat mCurrencyInstance = NumberFormat.getCurrencyInstance();

    //private GalleryGridFragment.ZoomProvider zoomProvider;
    private Context context;
    private final FragmentManager fragmentManager;
    private final BaseAppDisplayFactory baseAppDisplayFactory;

    //ItemUseCases itemUseCases;

    private List<ItemModel> items = new LinkedList<>();

    public GridItemsAdapter(Observable<ItemModel> itemsObservable, FragmentManager fragmentManager, BaseAppDisplayFactory baseAppDisplayFactory){
        super();
        this.fragmentManager = fragmentManager;
        this.baseAppDisplayFactory = baseAppDisplayFactory;
        //this.itemUseCases = itemUseCases;
        //this.zoomProvider = zoomProvider;

        /*if (mustGenerateSections()){
            generateSections();
        }*/

        itemsObservable.subscribe(this);
        //itemsHolderInterface.getItemHolder().addObserver(this);

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

        Item item = (Item) items.get(baseItemPosition);

        baseItemViewHolder.button.setOnClickListener(new GridItemOnClickListener(baseItemPosition));
        baseItemViewHolder.id.setText(item.getStringId());
        baseItemViewHolder.description.setText(item.getName());
        baseItemViewHolder.price.setText(mCurrencyInstance.format(item.getPrice()));

        ImagesHelper.loadCardImageWithGlide(context, item, baseItemViewHolder.image);

        if (item.mustShowPreviousPrice()) {
            baseItemViewHolder.fromPrice.setText(context.getString(
                    R.string.item_view_promoted_price,
                    mCurrencyInstance.format(item.getPreviousPrice())
            ));
        } else {
            baseItemViewHolder.fromPrice.setText("");
        }
    }

    @Override
    public int getItemViewType(int baseItemPosition) {
        Item item = (Item) items.get(baseItemPosition);

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

    public int findCategoryPositionInItems(CategoryModel categoryModel){

        /*for (int i = 0; i < itemsHolderInterface.getItemHolder().getSize(); i++){
            if (itemsHolderInterface.getItemHolder().getItemIn(i).getCategoryId().equals(baseCategory.getStringId())) {
                return i + 1;
            }
        }*/

        //if nothing found, return top position
        return 0;
    }

    @Override
    public void onCompleted() {
        //TODO
        //Stop loading
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(ItemModel itemModel) {
        items.add(itemModel);
        notifyItemInserted(items.size() -1);

    }




    private class BaseItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView id;
        public TextView price;
        public TextView description;
        public ImageButton button;
        public TextView fromPrice;

        public BaseItemViewHolder(View v){
            super(v);
            image = (ImageView) v.findViewById(R.id.item_image);
            id = (TextView) v.findViewById(R.id.item_id);
            price = (TextView) v.findViewById(R.id.item_price);
            description = (TextView) v.findViewById(R.id.item_description);
            button = (ImageButton) v.findViewById(R.id.item_view_detail_button);
            fromPrice = (TextView) v.findViewById(R.id.item_price_previous);
        }
    }



    private class GridItemOnClickListener implements View.OnClickListener, View.OnLongClickListener{
        private int position;

        public GridItemOnClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {

            baseAppDisplayFactory.switchToItemView(fragmentManager, position);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }


}