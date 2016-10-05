package com.glsebastiany.smartcatalogspl.core.presentation.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.glsebastiany.smartcatalogspl.core.data.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.presentation.BaseAppDisplayFactory;

public abstract class MainAdapterBase<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected final Context context;
    protected final BaseAppDisplayFactory baseAppDisplayFactory;

    public MainAdapterBase(Context context, BaseAppDisplayFactory baseAppDisplayFactory) {
        this.context = context;
        this.baseAppDisplayFactory = baseAppDisplayFactory;
    }

    public abstract void addItem(CategoryGroupModel categoryGroupModel);
}
