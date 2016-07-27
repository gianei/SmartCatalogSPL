package com.glsebastiany.smartcatalogspl.presentation.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import com.glsebastiany.smartcatalogspl.presentation.R;

public class ZoomableGridLayoutManager extends GridLayoutManager {

    public enum ZoomType {
        IN,
        OUT
    }

    private int gridSpanMin;
    private int gridSpanMax;

    public ZoomableGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        retrieveMinMaxSpan(context);
    }

    public ZoomableGridLayoutManager(Context context, int startingSpanCount) {
        super(context, startingSpanCount);
        retrieveMinMaxSpan(context);
    }

    public ZoomableGridLayoutManager(Context context, int startingSpanCount, int orientation, boolean reverseLayout) {
        super(context, startingSpanCount, orientation, reverseLayout);
        retrieveMinMaxSpan(context);
    }

    private void retrieveMinMaxSpan(Context context) {
        gridSpanMin = context.getResources().getInteger(R.integer.grid_span_min);
        gridSpanMax = context.getResources().getInteger(R.integer.grid_span_max);
    }

    private void updateSpanSize(int spanSize){
        setSpanCount(spanSize);
        requestLayout();
    }

    public int zoom(ZoomType zoomType){
        if (zoomType == ZoomType.IN)
            return zoomIn();
        else
            return zoomOut();
    }

    private int zoomIn(){
        int newSpanSize = getSpanCount();
        if (!isGridSpanAtMinimal()) {
            newSpanSize--;
            updateSpanSize(newSpanSize);
        }
        return newSpanSize;
    }

    private int zoomOut(){
        int newSpanSize = getSpanCount();
        if (!isGridSpanAtMaximum()) {
            newSpanSize++;
            updateSpanSize(newSpanSize);
        }
        return newSpanSize;
    }

    private boolean isGridSpanAtMinimal() {
        if (getSpanCount() <= gridSpanMin)
            return true;
        else
            return false;
    }

    private boolean isGridSpanAtMaximum() {
        if (getSpanCount() >= gridSpanMax)
            return true;
        else
            return false;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
                                       int position) {
        RecyclerView.SmoothScroller smoothScroller = new TopSnappedSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    private class TopSnappedSmoothScroller extends LinearSmoothScroller {

        //increased speed, remaining code from base class
        private final float MILLISECONDS_PER_PX;
        private static final float MILLISECONDS_PER_INCH = 15f;

        public TopSnappedSmoothScroller(Context context) {
            super(context);
            MILLISECONDS_PER_PX = calculateSpeedPerPixel(context.getResources().getDisplayMetrics());
        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return ZoomableGridLayoutManager.this
                    .computeScrollVectorForPosition(targetPosition);
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }

        /**
         * Copied from LinearSmoothScroller
         */
        @Override
        protected int calculateTimeForScrolling(int dx) {
            return (int) Math.ceil(Math.abs(dx) * MILLISECONDS_PER_PX);
        }

        /**
         * Copied from LinearSmoothScroller
         */
        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
        }

    }
}
