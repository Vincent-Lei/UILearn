package com.demo.ui.uiapplication.testdb;

import com.baza.android.slib.storage.database.annotation.ColumnAnnotation;
import com.baza.android.slib.storage.database.annotation.TableAnnotation;

/**
 * Created by Vincent.Lei on 2019/1/31.
 * Title：
 * Note：
 */
@TableAnnotation(tableName = "book")
public class Book {
    @ColumnAnnotation(columnName = "id", columnType = "INTEGER", primaryKey = 1)
    public int id;
    @ColumnAnnotation(columnName = "name")
    public String name;
    @ColumnAnnotation(columnName = "price", columnType = "FLOAT")
    public float price;

    @Override
    public String toString() {
        return "id = " + id + ";name = " + name + ";price = " + price;
    }
}
