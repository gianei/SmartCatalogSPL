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

package com.glsebastiany.ditlantaapp.data.firebase;


import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemExtendedModel;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.item.ItemBasicEntity;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.item.ItemExtendedEntity;

import java.util.Date;

public class FirebaseItem {
    public static final String LOCATION = "items";
    public static final String CHILD_UPDATED_DATE = "updatedDate";

    private static final long DAYS_SPAN_TO_BE_NEW_ITEM = 45;

    long category;
    long date;
    long id;
    String name;
    String photoUrl;
    long preference;
    double price;
    double previousPrice;
    boolean promoted;
    boolean sale;
    boolean assembled;
    long updatedDate;

    public long getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Required empty constructor
     */
    public FirebaseItem(){

    }

    public Long getCategory() {
        return category;
    }

    public long getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public long getPreference() {
        return preference;
    }

    public Double getPrice() {
        return price;
    }

    public Double getPreviousPrice() {
        return previousPrice;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public boolean isSale() {
        return sale;
    }

    public boolean isAssembled() {return assembled;}

    public ItemBasicModel toBasicItemModel(){
        return new ItemBasicEntity(
                id,
                category,
                name,
                getPrice().floatValue(),
                "descr",
                getPhotoUrl());
    }

    public ItemExtendedModel toPromotedItemModel(){
        return new ItemExtendedEntity(
                id,
                isPromoted(),
                isSale(),
                isAssembled(),
                getPreviousPrice().floatValue(),
                new Date(getDate()) );
    }

}
