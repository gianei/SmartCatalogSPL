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

package com.glsebastiany.smartcatalogspl.instancefood.memory.item;


import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;

public class MemoryItemEntity implements ItemBasicModel {

    private String id;
    private CategoryModel categoryModel;
    private String name;
    private float price;

    public MemoryItemEntity(String id, CategoryModel categoryModel, String name, float price) {
        this.id = id;
        this.categoryModel = categoryModel;
        this.name = name;
        this.price = price;
    }

    @Override
    public String getStringId() {
        return id;
    }

    @Override
    public String getCategoryStringId() {
        return categoryModel.getStringId();
    }

    @Override
    public CategoryModel getCategory() {
        return categoryModel;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return id + ":" + name;
    }

    @Override
    public String getImageUrl() {
        return getDescription() + ".jpg";
    }
}
