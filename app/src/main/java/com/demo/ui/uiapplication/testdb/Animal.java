package com.demo.ui.uiapplication.testdb;

import com.baza.android.slib.storage.database.annotation.ColumnAnnotation;
import com.baza.android.slib.storage.database.annotation.TableAnnotation;

/**
 * Created by Vincent.Lei on 2019/1/31.
 * Title：
 * Note：
 */
@TableAnnotation(tableName = "animal")
public class Animal {
    @ColumnAnnotation(columnName = "id", columnType = "INTEGER", primaryKey = 1)
    public int id;
    @ColumnAnnotation(columnName = "name")
    public String name;
    @ColumnAnnotation(columnName = "extra")
    public String extra;
}
