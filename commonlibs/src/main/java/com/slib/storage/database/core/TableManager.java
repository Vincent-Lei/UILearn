package com.slib.storage.database.core;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.slib.storage.database.annotation.ColumnAnnotation;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Vincent.Lei on 2016/11/8.
 * Title :
 * Note : 表结构管理（创建修改）
 */

public class TableManager {
    private static final String DEFAULT_DB_NAME = "app_data_db.db";
    public static String DB_NAME = DEFAULT_DB_NAME;
    private static final HashSet<String> mTableCheckedSet = new HashSet<>(8);

    public static void onDataBaseCreate(SQLiteDatabase db) {
    }

    public static void onDataBaseUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 创建表
     */
    private static void createTable(SQLiteDatabase db, Class<?> classZZ) {
        String tag = ",";
        String kg = " ";
        if (classZZ == null)
            return;
        String tableName = DbClassUtil.getTableNameByAnnotationClass(classZZ);
        if (TextUtils.isEmpty(tableName))
            throw new IllegalArgumentException("table name can not be null class = " + classZZ);
        StringBuilder sb = new StringBuilder("CREATE TABLE ").append(tableName.trim()).append(" (");
        List<Field> fieldList = DbClassUtil.getFieldList(classZZ);
        Field f;
        for (int i = 0, size = fieldList.size(); i < size; i++) {
            f = fieldList.get(i);
            if (f.isAnnotationPresent(ColumnAnnotation.class)) {
                ColumnAnnotation columnAnnotation = f.getAnnotation(ColumnAnnotation.class);
                String columnName = columnAnnotation.columnName();
                if (TextUtils.isEmpty(columnName))
                    throw new IllegalArgumentException("column name can not be null field = " + f);
                sb.append(kg).append(columnName).append(kg).append(columnAnnotation.columnType()).append(kg);
                if (columnAnnotation.primaryKey() > 0)
                    sb.append(" PRIMARY KEY ");
                if (columnAnnotation.autoIncrement() > 0)
                    sb.append(" AUTOINCREMENT ");
                sb.append(tag);
            }
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        String sql = sb.toString();
        Log.d("TableManager", "sql = " + sql);
        db.execSQL(sql);

    }

    public static void updateTable(SQLiteDatabase db, Class<?> classZZ) {
        String tableName = DbClassUtil.getTableNameByAnnotationClass(classZZ);
        if (TextUtils.isEmpty(tableName))
            throw new IllegalArgumentException("table name can not be null class = " + classZZ);
        if (mTableCheckedSet.contains(tableName))
            return;
        mTableCheckedSet.add(tableName);
        if (!isTableExist(db, tableName)) {
            //不存在该表  直接创建表
            Log.d("TableManager", "table is not exist and create :" + tableName);
            createTable(db, classZZ);
            return;
        }
        //存在该表  判断表结构是否修改（字段只增不减,类型不可修改）
        HashSet<String> columnSet = getColumnsOfTable(db, tableName);
        List<Field> fieldList = DbClassUtil.getFieldList(classZZ);
        Field field;
        String columnName;
        ColumnAnnotation columnAnnotation;
        for (int i = 0, size = fieldList.size(); i < size; i++) {
            field = fieldList.get(i);
            if (field.isAnnotationPresent(ColumnAnnotation.class)) {
                columnAnnotation = field.getAnnotation(ColumnAnnotation.class);
                columnName = columnAnnotation.columnName();
                if (columnSet.contains(columnName))
                    continue;
                //字段不存在，修改表结构
                Log.d("TableManager", columnName + "is not exist and add it");
                addColumn(db, tableName, columnName, columnAnnotation.columnType(), columnAnnotation.primaryKey() > 0);
            }
        }
    }


    /**
     * 判断表是否存在
     */
    private static boolean isTableExist(SQLiteDatabase db, String tableName) {
        //SELECT COUNT(*) FROM sqlite_master where type='table' and name='DBInfo'
        boolean result = false;
        String sql = "SELECT COUNT(*) FROM SQLITE_MASTER  WHERE TYPE = 'table' AND NAME = '" + tableName.trim() + "'";
        Log.d("TableManager", "sql :" + sql);
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToNext())
            result = cursor.getInt(0) > 0;
        Log.d("TableManager", tableName + " is exist :" + result);
        if (cursor != null)
            cursor.close();
        return result;
    }


    private static HashSet<String> getColumnsOfTable(SQLiteDatabase db, String tableName) {
        String sql = "select * from " + tableName + " where 0";
        Log.d("TableManager", "query table created sql : " + sql);
        Cursor cursor = db.rawQuery(sql, null);
        HashSet<String> columnSet = null;
        if (cursor != null) {
            columnSet = new HashSet<>(cursor.getColumnCount());
            String[] names = cursor.getColumnNames();
            Collections.addAll(columnSet, names);
            cursor.close();
        }
        return columnSet;
    }

    private static void addColumn(SQLiteDatabase db, String tableName, String columnName, String type, boolean isPrimaryKey) {
        String sql = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + type;
        Log.d("TableManager", "sql :" + sql);
        if (isPrimaryKey)
            sql += " PRIMARY KEY ";
        db.execSQL(sql);
    }


}

