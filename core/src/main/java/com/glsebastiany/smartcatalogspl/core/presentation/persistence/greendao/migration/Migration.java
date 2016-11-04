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

import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

public interface Migration {

    /**
     * Apply the migration to the given database
     *
     * @param db
     *            to be updated
     * @param currentVersion
     *            the current version before migration
     * @return the version after migration has been applied
     */
    int applyMigration(Database db, int currentVersion);

    /**
     * @return daoSession of the previous Migration required if the current
     *         version is to old for this migration. NB: This will only be null
     *         if this is the tip of the chain and there are no other earlier
     *         migrations.
     */
     Migration getPreviousMigration();

    /**
     * @return the target (old) version which will be migrated from.
     */
    int getTargetVersion();

    /**
     * @return the new version which will result from the migration being
     *         applied.
     */
    int getMigratedVersion();
}
