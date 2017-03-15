package com.example.bgfvg.qq.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BGFVG on 2017/3/15.
 */

public class DBUtils {

    private static Context sContext;
    private static boolean isInit;

    public static void init(Context context) {
        sContext = context;
        isInit = true;
    }

    public static List<String> getContacts(String username) {
        if (!isInit) {
            throw new RuntimeException("使用时前请初始化DBUtils");
        }
        ContactSQLiteOpenHelper contactSQLiteOpenHelper = new ContactSQLiteOpenHelper(sContext);
        SQLiteDatabase database = contactSQLiteOpenHelper.getReadableDatabase();
        Cursor query = database.query(ContactSQLiteOpenHelper.T_CONTACT, new String[]{ContactSQLiteOpenHelper.CONTACT}, ContactSQLiteOpenHelper.USERNAME + "=?", new String[]{username}, null, null, ContactSQLiteOpenHelper.CONTACT);
        List<String> contactList = new ArrayList<>();
        while (query.moveToNext()) {
            String contact = query.getString(0);
            contactList.add(contact);
        }
        query.close();
        database.close();
        return contactList;
    }

    /**
     * @param username
     * @param contactList
     */
    public static void updateContacts(String username, List<String> contactList) {
        ContactSQLiteOpenHelper contactSQLiteOpenHelper = new ContactSQLiteOpenHelper(sContext);
        SQLiteDatabase writableDatabase = contactSQLiteOpenHelper.getWritableDatabase();
        writableDatabase.beginTransaction();
        //书写落伍逻辑
        int delete = writableDatabase.delete(ContactSQLiteOpenHelper.T_CONTACT, ContactSQLiteOpenHelper.USERNAME + "=?", new String[]{username});
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactSQLiteOpenHelper.USERNAME, username);
        for (int i = 0; i < contactList.size(); i++) {
            //逐条插入数据
            String contact = contactList.get(i);
            contentValues.put(ContactSQLiteOpenHelper.CONTACT, contact);
            writableDatabase.insert(ContactSQLiteOpenHelper.T_CONTACT, null, contentValues);
        }
        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();
        writableDatabase.close();
    }

}
