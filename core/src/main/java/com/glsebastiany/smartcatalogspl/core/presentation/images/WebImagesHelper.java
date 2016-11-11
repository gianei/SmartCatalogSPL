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

package com.glsebastiany.smartcatalogspl.core.presentation.images;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.glsebastiany.smartcatalogspl.core.R;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.Utils;

public class WebImagesHelper extends ImagesHelperBase {
    @Override
    public void loadDetailImageWithGlide(Context context, ItemBasicModel baseItem, ImageView intoView) {
        Glide.with(context).load(Utils.getImageCompleteUrl() + baseItem.getImageUrl())
                .asBitmap()
                .signature(new StringSignature(baseItem.getStringId()))
                .placeholder(ContextCompat.getDrawable(context, R.drawable.image_placeholder))
                .fitCenter()
                .into(intoView);
    }

    @Override
    public void loadCardImageWithGlide(Context context, ItemBasicModel baseItem, ImageView intoView) {
        Glide.with(context).load(Utils.getImageCompleteUrl() + baseItem.getImageUrl())
                .asBitmap()
                .signature(new StringSignature(baseItem.getStringId()))
                .placeholder(ContextCompat.getDrawable(context, R.drawable.image_placeholder))
                .into(intoView);

    }
}
