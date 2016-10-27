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

package com.glsebastiany.ditlantaapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.glsebastiany.ditlantaapp.data.db.DaoMaster;
import com.glsebastiany.ditlantaapp.data.db.DaoSession;
import com.glsebastiany.ditlantaapp.data.migration.*;


public class GreenDaoOpenHelper extends DaoMaster.OpenHelper {

    public GreenDaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * Apply the appropriate migrations to update the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (newVersion) {
            case 15:
                new MigrateV14ToV15().applyMigration(db, oldVersion);
                break;
            case 16:
                new MigrateV15ToV16().applyMigration(db, oldVersion);
                break;
            case 17:
                new MigrateV16ToV17().applyMigration(db, oldVersion);
                break;
            case 18:
                new MigrateV17ToV18().applyMigration(db, oldVersion);
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
                    GreenDaoOpenHelper helper = new GreenDaoOpenHelper(context, "catalogue_db", null);
                    SQLiteDatabase mDb = helper.getWritableDatabase();
                    DaoMaster daoMaster = new DaoMaster(mDb);
                    ourInstanceDaoSession = daoMaster.newSession();
                }
            }
        }
        return ourInstanceDaoSession;
    }
}