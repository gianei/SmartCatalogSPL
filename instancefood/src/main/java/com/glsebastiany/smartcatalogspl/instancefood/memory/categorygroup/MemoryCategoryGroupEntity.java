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

package com.glsebastiany.smartcatalogspl.instancefood.memory.categorygroup;


import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.data.categorygroup.CategoryGroupModel;

import java.util.List;

public class MemoryCategoryGroupEntity implements CategoryGroupModel {

    private final String id;
    private final String name;
    private final String imageUrl;
    private final List<? extends CategoryModel> categoryModels;

    public MemoryCategoryGroupEntity(String id, String name, String imageUrl, List<? extends CategoryModel> categoryModels){
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.categoryModels = categoryModels;
    }

    @Override
    public String getStringId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getCategoryList() {
        StringBuilder categoriesStringBuilder = new StringBuilder();
        for (int c = 0; c < categoryModels.size() - 1; c++){
            categoriesStringBuilder.append(categoryModels.get(c).getStringId() + ",");
        }
        if (categoryModels.size() != 0)
            categoriesStringBuilder.append(categoryModels.get(categoryModels.size() - 1).getStringId());
        return categoriesStringBuilder.toString();
    }

    public String getName() {
        return name;
    }
}
