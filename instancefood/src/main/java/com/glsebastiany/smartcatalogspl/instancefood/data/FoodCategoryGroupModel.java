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

package com.glsebastiany.smartcatalogspl.instancefood.data;


import com.glsebastiany.smartcatalogspl.core.data.CategoryGroupModel;

import java.util.List;

public class FoodCategoryGroupModel implements CategoryGroupModel {

    private final String id;
    private final String name;
    private final String imageUrl;
    private final List<String> categoriesIds;

    public FoodCategoryGroupModel(String id, String name, String imageUrl, List<String> categoriesIds){
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.categoriesIds = categoriesIds;
    }

    @Override
    public String getId() {
        return id;
    }

    public List<String> getCategoriesIds() {
        return categoriesIds;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }
}
