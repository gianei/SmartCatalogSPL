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

package com.glsebastiany.smartcatalogspl.presentationfood.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;
import com.glsebastiany.smartcatalogspl.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.data.foods.FoodCategoryModel;
import com.glsebastiany.smartcatalogspl.presentation.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.presentation.system.Utils;
import com.glsebastiany.smartcatalogspl.presentationfood.R;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Observer;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolderSuitCase> implements Observer<CategoryModel>{
    private final Context context;
    private final BaseAppDisplayFactory baseAppDisplayFactory;

    private List<CategoryModel> suitCases = new LinkedList<>();

    public MainAdapter(Context context, Observable<CategoryModel> categoryObservable, BaseAppDisplayFactory baseAppDisplayFactory) {
        this.context = context;
        this.baseAppDisplayFactory = baseAppDisplayFactory;
        categoryObservable.subscribe(this);
    }

    @Override
    public ViewHolderSuitCase onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_card_suit_case, parent, false);

        return new ViewHolderSuitCase(v, this);
    }

    public CategoryModel getFromPosition(int adapterPosition){
        return suitCases.get(adapterPosition);
    }

    @Override
    public void onBindViewHolder(ViewHolderSuitCase viewHolderSuitCase, int position) {
        FoodCategoryModel model = (FoodCategoryModel)suitCases.get(position);

        viewHolderSuitCase.title.setText(model.getId());
        Glide.with(context).load("http://placekitten.com/g/200/200")
                .asBitmap()
                .placeholder(ContextCompat.getDrawable(context, R.drawable.image_placeholder))
                .signature(new StringSignature(Integer.toString(position)))
                .into(new SuitCaseViewTarget(context, viewHolderSuitCase));
    }


    @Override
    public int getItemCount() {
        return suitCases.size();
    }



    class SuitCaseViewTarget extends BitmapImageViewTarget {

        private final ViewHolderSuitCase viewHolder;
        private final Context context;

        public SuitCaseViewTarget(Context context, ViewHolderSuitCase viewHolder) {
            super(viewHolder.image);
            this.context = context;
            this.viewHolder = viewHolder;
        }

        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
            super.onResourceReady(bitmap, anim);
            Palette palette = Palette.from(bitmap).generate();
            int color = palette.getDarkMutedColor(ContextCompat.getColor(context, R.color.colorPrimary));
            color = Utils.adjustAlpha(color, 0.75f);

            viewHolder.title.setBackgroundColor(color);
        }
    }

    public static class ViewHolderSuitCase extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;

        public ViewHolderSuitCase(View v, final MainAdapter adapter){
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.title);


           v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.baseAppDisplayFactory.startGalleryActivity();
                }
            });
        }
    }


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(CategoryModel categoryModel) {
        suitCases.add(categoryModel);
        notifyItemInserted(suitCases.size() -1);
    }
}
