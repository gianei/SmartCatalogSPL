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

package com.glsebastiany.smartcatalogspl.core.presentation.persistence.firebase;

import com.glsebastiany.smartcatalogspl.core.data.categorygroup.CategoryGroupModel;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.categorygroup.CategoryGroupEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FirebaseSuitCase {
    public static final String LOCATION = "suitCases";
    public static final String CHILD_UPDATED_DATE = "updatedDate";

    Map<String, Object> categories;
    String name;
    String imageUrl;
    long updatedDate;
    long order;

    /**
     * Required empty constructor
     */
    public FirebaseSuitCase(){

    }

    public Map<String, Object> getCategories() {
        return categories;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public long getOrder(){
        return order;
    }

    public CategoryGroupModel toSuitCase() {
        List<Map.Entry<String, Object>> categoriesIds;
        if (categories != null)
            categoriesIds = new LinkedList<>( categories.entrySet() );
        else {
            categoriesIds = new LinkedList<>();

        }

        Collections.sort( categoriesIds, new Comparator<Map.Entry<String, Object>>()
        {
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2 )
            {
                return ((Long)o1.getValue()).compareTo((Long)o2.getValue());
            }
        } );


        StringBuilder categoriesStringBuilder = new StringBuilder();
        for (int c = 0; c < categoriesIds.size() - 1; c++){
            categoriesStringBuilder.append(categoriesIds.get(c).getKey() + ",");
        }
        if (categoriesIds.size() != 0)
            categoriesStringBuilder.append(categoriesIds.get(categoriesIds.size() - 1).getKey());

        return new CategoryGroupEntity((long)name.hashCode(), name, imageUrl, categoriesStringBuilder.toString(), order);

    }
}
