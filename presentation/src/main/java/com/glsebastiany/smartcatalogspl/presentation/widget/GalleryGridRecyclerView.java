package com.glsebastiany.smartcatalogspl.presentation.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.glsebastiany.smartcatalogspl.presentation.R;


public class GalleryGridRecyclerView extends RecyclerView {

    private final int mMinFlingVelocity = 0;
    private final int mMaxFlingVelocity = getResources().getDimensionPixelSize(R.dimen.max_gallery_flinging);

    public GalleryGridRecyclerView(Context context) {
        super(context);
    }

    public GalleryGridRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryGridRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        if (Math.abs(velocityX) < mMinFlingVelocity) {
            velocityX = 0;
        }
        if (Math.abs(velocityY) < mMinFlingVelocity) {
            velocityY = 0;
        }
        velocityX = Math.max(-mMaxFlingVelocity, Math.min(velocityX, mMaxFlingVelocity));
        velocityY = Math.max(-mMaxFlingVelocity, Math.min(velocityY, mMaxFlingVelocity));
        if (velocityX != 0 || velocityY != 0) {

            return super.fling(velocityX, velocityY);
        }
        return false;
    }
}
