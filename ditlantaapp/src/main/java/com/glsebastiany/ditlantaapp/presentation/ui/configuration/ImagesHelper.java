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

package com.glsebastiany.ditlantaapp.presentation.ui.configuration;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.glsebastiany.ditlantaapp.R;
import com.glsebastiany.ditlantaapp.data.FileServices;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.configuration.BaseAppDisplayFactory;
import com.glsebastiany.smartcatalogspl.core.presentation.ui.widget.Utils;

import java.io.File;

public class ImagesHelper implements BaseAppDisplayFactory.ImagesHelper {

    @NonNull
    private static String getImageCompleteUrl(Context context, ItemBasicModel myListItem) {
        return FileServices.getFotosFolder(context)
                + File.separator + myListItem.getStringId() + ".jpg";
    }

    @NonNull
    private static MediaStoreSignature getFileSignature(String imageCompleteUrl) {
        File file = new File(imageCompleteUrl);
        long modifiedSignature;
        if (file.exists())
            modifiedSignature = file.lastModified();
        else
            modifiedSignature = 0L;

        return new MediaStoreSignature("", modifiedSignature, 0);
    }

    @Override
    public void loadDetailImageWithGlide(Context context, ItemBasicModel baseItem, ImageView intoView){
        String imageCompleteUrl = ImagesHelper.getImageCompleteUrl(context, baseItem);
        Glide.with(context).load(imageCompleteUrl)
                .asBitmap()
                .signature(ImagesHelper.getFileSignature(imageCompleteUrl))
                .placeholder(Utils.getDrawable(context, R.drawable.image_placeholder))
                .fitCenter()
                .into(intoView);
    }

    @Override
    public void loadCardImageWithGlide(Context context, ItemBasicModel baseItem, ImageView intoView){
        String imageCompleteUrl = ImagesHelper.getImageCompleteUrl(context, baseItem);
        Glide.with(context).load(imageCompleteUrl)
                .asBitmap()
                .signature(ImagesHelper.getFileSignature(imageCompleteUrl))
                .placeholder(Utils.getDrawable(context, R.drawable.image_placeholder))
                .into(intoView);
    }


}
