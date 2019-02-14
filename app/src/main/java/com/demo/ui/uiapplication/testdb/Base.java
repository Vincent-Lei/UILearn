package com.demo.ui.uiapplication.testdb;

import com.slib.storage.database.annotation.ColumnAnnotation;

/**
 * Created by Vincent.Lei on 2019/2/1.
 * Title：
 * Note：
 */
public class Base {
    @ColumnAnnotation(columnName = "id",columnType = "INTEGER",primaryKey = 1)
    public int id;
    @ColumnAnnotation(columnName = "name")
    public String name;
}
