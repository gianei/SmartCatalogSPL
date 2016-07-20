package com.glsebastiany.smartcatalogspl.domain;


import com.glsebastiany.smartcatalogspl.data.cars.CarItemModel;
import com.glsebastiany.smartcatalogspl.data.cars.CarItemsItemRepository;
import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.data.ItemRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

@Singleton
public class FerrariItemUseCases implements ItemUseCases {

    private ItemRepository itemRepository;

    @Inject
    public FerrariItemUseCases(CarItemsItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Override
    public Observable<ItemModel> allItems(){
        return itemRepository.getAll();
        //here we can filter or map or whatever
    }

    @Override
    public Observable<ItemModel> mainViewItems(){
        return itemRepository.getAll().filter(new Func1<ItemModel, Boolean>() {
            @Override
            public Boolean call(ItemModel s) {
                CarItemModel item =  (CarItemModel) s;
                return item.getCompany().equals("Ferrari");
            }
        });
        //here we can filter or map or whatever
    }
}
