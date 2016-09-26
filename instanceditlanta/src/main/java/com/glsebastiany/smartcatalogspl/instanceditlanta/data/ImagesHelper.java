package com.glsebastiany.smartcatalogspl.instanceditlanta.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.glsebastiany.smartcatalogspl.core.Utils;
import com.glsebastiany.smartcatalogspl.core.data.ItemModel;
import com.glsebastiany.smartcatalogspl.instanceditlanta.R;

import java.io.File;

public class ImagesHelper {

    @NonNull
    private static String getImageCompleteUrl(Context context, ItemModel myListItem) {
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

    public static void loadDetailImageWithGlide(Context context, ItemModel baseItem, ImageView intoView){
        String imageCompleteUrl = ImagesHelper.getImageCompleteUrl(context, baseItem);
        Glide.with(context).load(imageCompleteUrl)
                .asBitmap()
                .signature(ImagesHelper.getFileSignature(imageCompleteUrl))
                .placeholder(Utils.getDrawable(context, R.drawable.image_placeholder))
                .fitCenter()
                .into(intoView);
    }

    public static void loadCardImageWithGlide(Context context, ItemModel baseItem, ImageView intoView){
        String imageCompleteUrl = ImagesHelper.getImageCompleteUrl(context, baseItem);
        Glide.with(context).load(imageCompleteUrl)
                .asBitmap()
                .signature(ImagesHelper.getFileSignature(imageCompleteUrl))
                .placeholder(Utils.getDrawable(context, R.drawable.image_placeholder))
                .into(intoView);
    }


}
