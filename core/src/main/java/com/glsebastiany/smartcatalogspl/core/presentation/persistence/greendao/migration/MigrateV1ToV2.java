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

package com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.migration;

import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.category.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class MigrateV1ToV2 extends MigrationImpl {

    /**
     * {@inheritDoc}
     */
    @Override
    public int applyMigration(Database db,
                              int currentVersion) {
        prepareMigration(db, currentVersion);

        //db.execSQL("ALTER TABLE ITEM_BASIC_ENTITY ADD ASCII_NAME TEXT;");
        //instead of adding fields, clean the database to force repopulation of fields
        DaoMaster.dropAllTables(db, false);
        DaoMaster.createAllTables(db, false);

        return getMigratedVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTargetVersion() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMigratedVersion() {
        return 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Migration getPreviousMigration() {
        return null;
    }
}