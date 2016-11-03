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

package com.glsebastiany.smartcatalogspl.instancefood.memory.category;


import com.glsebastiany.smartcatalogspl.core.data.category.CategoryModel;

import java.util.List;

public class MemoryCategoryModel implements CategoryModel{

    private String id;
    private String parentId;
    private String name;
    private List<? extends CategoryModel> children;


    public MemoryCategoryModel(String id, String parentId, String name, List<? extends CategoryModel> children){
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.children = children;
    }

    @Override
    public String getStringId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getParentStringId() {
        return parentId;
    }

    @Override
    public List<? extends CategoryModel> getChildren() {
        return children;
    }


}
