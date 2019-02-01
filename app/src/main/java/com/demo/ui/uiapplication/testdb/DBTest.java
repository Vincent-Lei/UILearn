package com.demo.ui.uiapplication.testdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.baza.android.slib.storage.database.DBWorker;
import com.baza.android.slib.storage.database.core.DataBaseManager;
import com.baza.android.slib.storage.database.core.DbClassUtil;
import com.baza.android.slib.storage.database.handler.IDBControllerHandler;
import com.baza.android.slib.storage.database.listener.IDBReplyListener;
import com.demo.ui.uiapplication.LogUtil;

import java.util.List;

/**
 * Created by Vincent.Lei on 2019/1/31.
 * Title：
 * Note：
 */
public class DBTest {
    public static void testDb(Context context) {
        DBWorker.initDataBase(context, "test_db_db.db");
//        LogUtil.d("start to test db");
//        Animal animal = new Animal();
//        animal.id = 1;
//        animal.name = "Animal 1";
//        animal.extra = "好哈达中时代峻峰绿地控股";
//        DBWorker.saveSingle(animal, null, " id = ?", new String[]{animal.id + ""}, null, false);
//
        Book book = new Book();
//        book.id = 1;
//        book.name = "JAVA";
//        book.price = 45.50f;
//        DBWorker.saveSingle(book, null, null, null, new IDBReplyListener<Void>() {
//            @Override
//            public void onDBReply(Void aVoid) {
//                LogUtil.d("save book finished");
//            }
//        }, true);
//
//        List<Book> bookList = new ArrayList<>();
//        List<String> whereClauseList = new ArrayList<>();
//        List<String[]> whereArgsList = new ArrayList<>();
//        for (int i = 2; i < 101; i++) {
//            book = new Book();
//            book.id = i;
//            book.name = "Book@" + i;
//            book.price = 5.5f * i;
//            bookList.add(book);
//            whereClauseList.add(" id = ?");
//            whereArgsList.add(new String[]{String.valueOf(book.id)});
//        }
//        DBWorker.saveList(bookList, null, whereClauseList, whereArgsList, new IDBReplyListener<Void>() {
//            @Override
//            public void onDBReply(Void aVoid) {
//                LogUtil.d("save bookList finished");
//            }
//        }, true);
//
//        customerTest();

//        List<Child> childList = new ArrayList<>(100);
//        Child child;
//        for (int i = 0; i < 100; i++) {
//            child = new Child();
//            child.id = i;
//            child.name = "Child_" + i;
//            child.email = "email" + i + "@163.com";
//            child.mobilePhone = String.valueOf(15602970000l + i);
//            childList.add(child);
//        }
//        DBWorker.saveList(childList, null, null, null, new IDBReplyListener<Void>() {
//            //            @Override
//            public void onDBReply(Void aVoid) {
//                LogUtil.d("save childList finished");
//            }
//        }, true);

        testDelete();
        testQuery();
    }


    public static void customerTest() {
        Book book = new Book();
        book.id = 1;
        book.name = "JAVA11@#@";
        book.price = 450.50f;
        DBWorker.customerDBTask(book, new IDBControllerHandler<Book, Void>() {
            @Override
            public Void operateDataBaseAsync(DataBaseManager mDBManager, Book input) {
                String tableName = DbClassUtil.getTableNameByAnnotationClass(Book.class);
                Cursor cursor = mDBManager.query(tableName, null, " id = ? ", new String[]{String.valueOf(input.id)}, null, null, null, " 0,1 ");
                boolean exist = false;
                if (cursor != null) {
                    exist = cursor.getCount() > 0;
                    cursor.close();
                }
                ContentValues contentValues = DbClassUtil.buildContentValues(input, null);
                if (exist)
                    mDBManager.update(tableName, contentValues, " id = ? ", new String[]{String.valueOf(input.id)});
                else
                    mDBManager.insert(tableName, contentValues);
                return null;
            }

            @Override
            public Class<?> getDependModeClass() {
                return Book.class;
            }
        }, new IDBReplyListener<Void>() {
            @Override
            public void onDBReply(Void aVoid) {
                LogUtil.d("save customer book finished");
            }
        }, true);
    }

    private static void testDelete() {
        DBWorker.delete(Book.class, "id = ?", new String[]{"75"});
    }

    private static void testQuery() {
        DBWorker.query(Book.class, null, " id in (?,?,?,?)", new String[]{"75", "76", "77", "78"}, null, null, null, null, new IDBReplyListener<List<Book>>() {
            @Override
            public void onDBReply(List<Book> books) {
                if (books != null) {
                    for (int i = 0, size = books.size(); i < size; i++) {
                        LogUtil.d(books.get(i).toString());
                    }
                }
            }
        }, true);

        DBWorker.query(Book.class, new String[]{"id", "name"}, null, null, null, null, null, null, new IDBReplyListener<List<Book>>() {
            @Override
            public void onDBReply(List<Book> books) {
                if (books != null) {
                    for (int i = 0, size = books.size(); i < size; i++) {
                        LogUtil.d(books.get(i).toString());
                    }
                }
            }
        }, true);

        DBWorker.query(Child.class, null, null, new IDBReplyListener<List<Child>>() {
            @Override
            public void onDBReply(List<Child> childList) {
                if (childList != null) {
                    for (int i = 0, size = childList.size(); i < size; i++) {
                        LogUtil.d(childList.get(i).toString());
                    }
                }
            }
        });
    }
}
