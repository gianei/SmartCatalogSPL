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

package com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.categorygroup;


import com.glsebastiany.smartcatalogspl.core.data.categorygroup.CategoryGroupModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class CategoryGroupEntity implements CategoryGroupModel{

    @Id
    private Long id;

    private String name;

    private String imageUrl;

    private String categoryList;

    private Long order;

    @Generated(hash = 2092125216)
    public CategoryGroupEntity(Long id, String name, String imageUrl,
            String categoryList, Long order) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.categoryList = categoryList;
        this.order = order;
    }

    @Generated(hash = 533722429)
    public CategoryGroupEntity() {
    }

    public Long getId() {
        return this.id;
    }

    @Override
    @Keep
    public String getStringId() {
        return this.id.toString();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryList() {
        return this.categoryList;
    }

    public void setCategoryList(String categoryList) {
        this.categoryList = categoryList;
    }

    public Long getOrder() {
        return this.order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

}
