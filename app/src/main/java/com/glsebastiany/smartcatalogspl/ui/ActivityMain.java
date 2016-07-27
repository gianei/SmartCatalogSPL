package com.glsebastiany.smartcatalogspl.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.di.BaseActivity;
import com.glsebastiany.smartcatalogspl.domain.ItemUseCases;
import com.glsebastiany.smartcatalogspl.presentation.DisplayFactory;
import com.glsebastiany.smartcatalogspl.presentation.ItemViewHolder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.functions.Action0;
import rx.functions.Action1;

@EActivity(R.layout.activity_main)
public class ActivityMain extends BaseActivity {

    @InstanceState
    boolean fromSavedInstance = false;

    @Inject
    ItemUseCases itemUseCases;

    @Inject
    DisplayFactory displayFactory;

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    @AfterViews
    public void afterViews(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(itemUseCases.mainViewItems()));
    }


    Observer<String> myObserver = new Observer<String>() {
        @Override
        public void onCompleted() {
            // Called when the observable has no more data to emit
        }

        @Override
        public void onError(Throwable e) {
            // Called when the observable encounters an error
        }

        @Override
        public void onNext(String s) {
            // Called each time the observable emits data
            Log.d("MY OBSERVER", s);
        }
    };

    @Override
    protected void initializeInjector() {
        {
            getApplicationComponent().inject(this);

            if (!fromSavedInstance){
                getAndroidApplication().createGalleryComponent(itemUseCases.allItems());
                fromSavedInstance = true;
            }

        }
    }


    public class MyAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        private List<ItemModel> mDataset = new LinkedList<>();

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mTextView;
            public ViewHolder(TextView v) {
                super(v);
                mTextView = v;
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(final Observable<ItemModel> myDataset) {
            myDataset.subscribe(
                    //on added
                    new Action1<ItemModel>() {
                        @Override
                        public void call(ItemModel string) {
                            mDataset.add(string);
                            notifyDataSetChanged();
                        }
                    },
                    // on error
                    new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            //mDataset = Arrays.asList("erro: " + throwable.getMessage());
                            notifyDataSetChanged();
                        }
                    },
                    //on compleeted
                    new Action0() {
                        @Override
                        public void call() {
                            mDataset.remove(0);
                            notifyDataSetChanged();
                        }
                    });
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
            return displayFactory.createItemViewHolder(parent);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {

            ItemModel itemModel = mDataset.get(position);

            holder.print(itemModel);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
