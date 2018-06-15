package com.myappl.usesqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public final static String DB_NAME = "myabook";
    public final static int DB_VERSION = 1;

    public MySQLiteOpenHelper(Context context, String name, int version) {
        // factory = null : SQLiteDatabase.CursorFactory: to use for creating cursor objects, or null for the default
        super(context, DB_NAME, null, DB_VERSION);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, DB_NAME, factory, DB_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";
        sql += "create table MyABook (";
        sql += " user_id integer primary key AUTOINCREMENT";
        sql += ",first_name text not null";
        sql += ",last_name text";
        sql += ",address text";
        sql += ",age integer";
        sql += ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
