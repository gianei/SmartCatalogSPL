package com.glsebastiany.smartcatalogspl.data;

import rx.Observable;

public interface ItemRepository {
    Observable<ItemModel> getAll();
}
