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

package com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.category.DaoMaster;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.category.DaoSession;
import com.glsebastiany.smartcatalogspl.core.presentation.persistence.greendao.migration.MigrateV0ToV1;

import org.greenrobot.greendao.database.Database;


public class GreenDaoOpenHelper extends DaoMaster.OpenHelper {

    public GreenDaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * Apply the appropriate migrations to update the database.
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        switch (newVersion) {
            case 1:
                new MigrateV0ToV1().applyMigration(db, oldVersion);
                break;
            default:
                return;
        }
    }




    private static final Object mutex= new Object();
    private static DaoSession ourInstanceDaoSession = null;

    public static DaoSession daoSession(Context context) {
        if(ourInstanceDaoSession == null){
            synchronized (mutex){
                if(ourInstanceDaoSession == null){
                    GreenDaoOpenHelper helper = new GreenDaoOpenHelper(context, "smart_catalog_db", null);
                    SQLiteDatabase mDb = helper.getWritableDatabase();
                    DaoMaster daoMaster = new DaoMaster(mDb);
                    ourInstanceDaoSession = daoMaster.newSession();
                }
            }
        }
        return ourInstanceDaoSession;
    }
}