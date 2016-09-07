/*
 *     SmartCatalogSPL, an Android catalog Software Product Line
 *     Copyright (c) 2016 Gianei Leandro Sebastiany
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.glsebastiany.smartcatalogspl.ui.tabedgallery.swipablevisualization.detail;

import android.widget.TextView;

import com.glsebastiany.smartcatalogspl.R;
import com.glsebastiany.smartcatalogspl.data.ItemModel;
import com.glsebastiany.smartcatalogspl.di.BaseFragment;
import com.glsebastiany.smartcatalogspl.di.components.ItemsGroupComponent;
import com.glsebastiany.smartcatalogspl.di.helper.HasComponent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

@EFragment(R.layout.fragment_gallery_visualization_detail_item)
public class FragmentItemDetail extends BaseFragment {

    @Inject
    Observable<ItemModel> itemModelObservable;

    @FragmentArg
    int position;

    @ViewById(R.id.textViewDetailDescription)
    TextView descriptionText;

    public static FragmentItemDetail newInstance(int position){
        return FragmentItemDetail_.builder().position(position).build();
    }

    @AfterViews
    public void afterViews() {


        itemModelObservable.subscribe(new Observer<ItemModel>() {

            private List<ItemModel> items = new LinkedList<>();

            @Override
            public void onCompleted() {
                descriptionText.setText(items.get(position).getId());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ItemModel itemModel) {
                items.add(itemModel);
            }
        });
    }


    @Override
    protected void initializeInjector() {
        ((HasComponent<ItemsGroupComponent>) getParentFragment()).getComponent().inject(this);
    }
}
