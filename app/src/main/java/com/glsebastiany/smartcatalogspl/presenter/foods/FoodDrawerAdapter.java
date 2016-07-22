package com.glsebastiany.smartcatalogspl.presenter.foods;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.data.CategoryModel;
import com.glsebastiany.smartcatalogspl.domain.CategoryUseCases;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;

public class FoodDrawerAdapter extends BaseAdapter{

    private Context context;
    CategoryUseCases categoryUseCases;
    private List<CategoryModel> items = new LinkedList<>();

    @Inject
    public FoodDrawerAdapter(Context context, CategoryUseCases categoryUseCases) {
        this.context = context;
        this.categoryUseCases = categoryUseCases;

        updateItems();
    }


    private void updateItems(){
        categoryUseCases.drawerCategories().subscribe(new Observer<CategoryModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException(e);
            }

            @Override
            public void onNext(CategoryModel categoryModel) {
                items.add(categoryModel);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position).getId();
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        view = ((LayoutInflater)context.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.view_gallery_drawer_list_item, parent, false);

        ((TextView) view).setText(items.get(position).getId());
        //if(items.get(position).getParentId() != parentCategory.getId())
        //    view.setPadding((int) (TEXT_VIEW_LEFT_PADDING_DP * context.getResources().getDisplayMetrics().density),0 ,0 ,0 );

        return view;
    }
}
