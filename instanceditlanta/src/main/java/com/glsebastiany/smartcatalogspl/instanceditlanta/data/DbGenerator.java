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

package com.glsebastiany.smartcatalogspl.instanceditlanta.data;

//dao generator usees freemarker that causes build errors
//import de.greenrobot.daogenerator.*;

public class DbGenerator {
    public static void main(String[] args) throws Exception{
        /*Schema schema = new Schema(18, "com.glsebastiany.smartcatalogspl.instanceditlanta.data.db");
        schema.enableKeepSectionsByDefault();

        Entity category = setupCategory(schema);

        setupItem(schema, category);

        setupSuitCase(schema, category);


        generateAll(schema);*/
    }

    /*private static Entity setupCategory(Schema schema) {
        // Makes categories as a tree
        Entity category = schema.addEntity("Category");
        category.implementsInterface("CategoryModel");

        category.addIdProperty(); //current greendDao a long ID
        Property parentIdProperty = category.addLongProperty("parentId").getProperty();
        category.addToOne(category, parentIdProperty).setName("parent");
        category.addToMany(category, parentIdProperty).setName("children");
        category.addStringProperty("name").notNull();
        return category;
    }

    private static void setupItem(Schema schema, Entity category) {
        Entity item = schema.addEntity("Item");
        item.implementsInterface("ItemModel");

        item.addIdProperty(); //current greendDao requires a long ID
        item.addStringProperty("name").notNull();
        item.addFloatProperty("price").notNull();
        item.addStringProperty("description").notNull();
        item.addStringProperty("imageUrl").notNull();
        item.addBooleanProperty("isPromoted").notNull();
        item.addBooleanProperty("isSale").notNull();
        item.addBooleanProperty("isAssembled").notNull();
        item.addFloatProperty("previousPrice").notNull();
        Property categoryIdProperty = item.addLongProperty("searchQuery").notNull().index().getProperty();
        item.addToOne(category, categoryIdProperty, "category");
        item.addDateProperty("creationDate").notNull();
    }

    private static void setupSuitCase(Schema schema, Entity category) {
        /*Entity suitCase = schema.addEntity("SuitCase");
        suitCase.implementsInterface("CategoryGroupModel");

        suitCase.addIdProperty(); //current greendDao requires a long ID
        suitCase.addStringProperty("name").notNull();
        suitCase.addStringProperty("imageUrl").notNull();
        suitCase.addStringProperty("categoryList").notNull();
        suitCase.addLongProperty("order").notNull();
    }

    private static void generateAll(Schema schema) throws Exception {
        new DaoGenerator().generateAll(schema, "instanceditlanta/src/main/java/");
    }*/

}
