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

package com.glsebastiany.smartcatalogspl.core.presentation.ui.homescreen.categorygroups;

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
import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.data.categorygroup.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.tabbedgallery.TabbedGalleryActivityBase;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.Utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivityCategoryGroupsAdapter extends RecyclerView.Adapter<MainActivityCategoryGroupsAdapter.ViewHolderSuitCase> {
    protected final Context context;
    protected final BaseAppDisplayFactory baseAppDisplayFactory;
    private List<CategoryGroupModel> categoriesGroup = new LinkedList<>();

    public MainActivityCategoryGroupsAdapter(Context context, BaseAppDisplayFactory baseAppDisplayFactory) {
        this.context = context;
        this.baseAppDisplayFactory = baseAppDisplayFactory;
    }

    @Override
    public ViewHolderSuitCase onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_card_suit_case, parent, false);

        return new ViewHolderSuitCase(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolderSuitCase viewHolderSuitCase, int position) {
        CategoryGroupModel model = categoriesGroup.get(position);


        viewHolderSuitCase.itemView.setOnClickListener(v -> {
            String categories = categoriesGroup.get(viewHolderSuitCase.getAdapterPosition()).getCategoryList();
            String[] catArray = categories.split(",");

            TabbedGalleryActivityBase.start(context, Arrays.asList(catArray));

        });

        viewHolderSuitCase.title.setText(model.getName());
        Glide.with(context).load(model.getImageUrl())
                .asBitmap()
                .placeholder(ContextCompat.getDrawable(context, R.drawable.image_placeholder))
                .signature(new StringSignature(Integer.toString(position)))
                .into(new SuitCaseViewTarget(context, viewHolderSuitCase));
    }

    @Override
    public int getItemCount() {
        return categoriesGroup.size();
    }

    public void addItem(CategoryGroupModel categoryGroupModel) {
        categoriesGroup.add(categoryGroupModel);
        notifyItemInserted(categoriesGroup.size()-1);
    }

    public static class ViewHolderSuitCase extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;

        public ViewHolderSuitCase(View v){
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.title);
        }
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
}
