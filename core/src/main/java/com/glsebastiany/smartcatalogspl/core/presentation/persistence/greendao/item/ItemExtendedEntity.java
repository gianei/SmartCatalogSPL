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

package com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.item;

import com.glsebastiany.smartcatalogspl.core.data.item.ItemBasicModel;
import com.glsebastiany.smartcatalogspl.core.data.item.ItemExtendedModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.category.DaoSession;

@Entity
public class ItemExtendedEntity implements ItemExtendedModel {

    @Transient
    private static final long DAYS_SPAN_TO_BE_NEW_ITEM = 30;

    @Id
    private Long id;

    @ToOne(joinProperty = "id")
    private ItemBasicEntity itemBasicModel;

    private boolean isPromoted;

    private boolean isSale;

    private boolean isAssembled;

    private float previousPrice;

    private java.util.Date creationDate;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 791978694)
    private transient ItemExtendedEntityDao myDao;

    @Generated(hash = 2115138696)
    public ItemExtendedEntity(Long id, boolean isPromoted, boolean isSale, boolean isAssembled,
            float previousPrice, java.util.Date creationDate) {
        this.id = id;
        this.isPromoted = isPromoted;
        this.isSale = isSale;
        this.isAssembled = isAssembled;
        this.previousPrice = previousPrice;
        this.creationDate = creationDate;
    }

    @Generated(hash = 301400167)
    public ItemExtendedEntity() {
    }

    @Generated(hash = 701498120)
    private transient Long itemBasicModel__resolvedKey;

    @Keep
    public String getStringId(){
        return id.toString();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsPromoted() {
        return this.isPromoted;
    }

    public void setIsPromoted(boolean isPromoted) {
        this.isPromoted = isPromoted;
    }

    public boolean getIsSale() {
        return this.isSale;
    }

    public void setIsSale(boolean isSale) {
        this.isSale = isSale;
    }

    public boolean getIsAssembled() {
        return this.isAssembled;
    }

    @Override
    @Keep
    public boolean getIsNew() {
        Date newItemFromDate = new Date();
        newItemFromDate.setTime(new Date().getTime() - getNewItemMilisecondsRange());
        return getCreationDate() != null && getCreationDate().after(newItemFromDate);
    }

    @Keep
    private static long getNewItemMilisecondsRange() {
        return 1000L * 60L * 60L * 24l * DAYS_SPAN_TO_BE_NEW_ITEM;
    }

    public void setIsAssembled(boolean isAssembled) {
        this.isAssembled = isAssembled;
    }

    public float getPreviousPrice() {
        return this.previousPrice;
    }

    @Override
    @Keep
    public boolean mustShowPreviousPrice() {
        return (isPromoted || isSale)
                && ((ItemBasicModel) workAroundBasicEntity()).getPrice() < getPreviousPrice();
    }

    public void setPreviousPrice(float previousPrice) {
        this.previousPrice = previousPrice;
    }

    public java.util.Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(java.util.Date creationDate) {
        this.creationDate = creationDate;
    }

    @Keep
    // code is copy of getItemBasicModel due to gradle error
    // hash = 246001278
    private Object workAroundBasicEntity(){
        Long __key = this.id;
        if (itemBasicModel__resolvedKey == null || !itemBasicModel__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ItemBasicEntityDao targetDao = daoSession.getItemBasicEntityDao();
            ItemBasicEntity itemBasicModelNew = targetDao.load(__key);
            synchronized (this) {
                itemBasicModel = itemBasicModelNew;
                itemBasicModel__resolvedKey = __key;
            }
        }
        return itemBasicModel;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 246001278)
    public ItemBasicEntity getItemBasicModel() {
        Long __key = this.id;
        if (itemBasicModel__resolvedKey == null || !itemBasicModel__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ItemBasicEntityDao targetDao = daoSession.getItemBasicEntityDao();
            ItemBasicEntity itemBasicModelNew = targetDao.load(__key);
            synchronized (this) {
                itemBasicModel = itemBasicModelNew;
                itemBasicModel__resolvedKey = __key;
            }
        }
        return itemBasicModel;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1658737294)
    public void setItemBasicModel(ItemBasicEntity itemBasicModel) {
        synchronized (this) {
            this.itemBasicModel = itemBasicModel;
            id = itemBasicModel == null ? null : itemBasicModel.getId();
            itemBasicModel__resolvedKey = id;
        }
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 768884704)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getItemExtendedEntityDao() : null;
    }
    
}
