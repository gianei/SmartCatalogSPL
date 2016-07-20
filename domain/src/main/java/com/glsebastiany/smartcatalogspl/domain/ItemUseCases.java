package com.glsebastiany.smartcatalogspl.domain;

import com.glsebastiany.smartcatalogspl.data.ItemModel;

import rx.Observable;

public interface ItemUseCases {
    Observable<ItemModel> allItems();
    Observable<ItemModel> mainViewItems();
}
