package com.glsebastiany.smartcatalogspl.domain;


import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.data.ItemRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DefaultItemUseCases implements ItemUseCases {

    private ItemRepository itemRepository;

    @Inject
    public DefaultItemUseCases(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Override
    public Observable<ItemModel> allItems(){
        return itemRepository.getAll();
        //here we can filter or map or whatever
    }

    @Override
    public Observable<ItemModel> mainViewItems() {
        return allItems();
    }
}
