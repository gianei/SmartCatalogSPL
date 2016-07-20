package com.glsebastiany.smartcatalogspl.data.foods;


import android.os.Handler;

import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.data.ItemRepository;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class FoodItemsItemRepository implements ItemRepository {

    @Inject //must have constructor so that it can be magically created
    public FoodItemsItemRepository(){}

    @Override
    public Observable<ItemModel> getAll(){
        return Observable.create(new Observable.OnSubscribe<ItemModel>(){
            @Override
            public void call(final Subscriber<? super ItemModel> subscriber) {
                try {
                    final List<ItemModel> userStr = getFoods();

                    for (ItemModel item :
                            userStr) {
                        subscriber.onNext(item);
                    }

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            subscriber.onCompleted();
                        }
                    }, 2000);
                } catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    private List<ItemModel> getFoods() {
        List<ItemModel> items = new LinkedList<>();
        items.add(new FoodItemModel("Fetuccini"));
        items.add(new FoodItemModel("Tagliateri"));
        items.add(new FoodItemModel("Sauce"));
        items.add(new FoodItemModel("Peanuts"));
        return items;
    }
}
