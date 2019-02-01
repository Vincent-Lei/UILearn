package com.demo.ui.uiapplication.testdb;

import com.baza.android.slib.storage.database.annotation.ColumnAnnotation;
import com.baza.android.slib.storage.database.annotation.TableAnnotation;

/**
 * Created by Vincent.Lei on 2019/2/1.
 * Title：
 * Note：
 */
@TableAnnotation(tableName = "child")
public class Child extends Base {
    @ColumnAnnotation(columnName = "email")
    public String email;
    @ColumnAnnotation(columnName = "mobilePhone")
    public String mobilePhone;

    @Override
    public String toString() {
        return "id = " + id + ";name = " + name + ";email = " + email + ";mobilePhone = " + mobilePhone;
    }
}
