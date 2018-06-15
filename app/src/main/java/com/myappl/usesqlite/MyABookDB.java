package com.myappl.usesqlite;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

//
//
//exportSchema：デフォルトではtrueですが、バージョンの履歴を保持したくない場合（インメモリのみのデータベースなど）、データベースに対して無効にすることができます。
//
@Database( entities = { MyABook.class }, version = 1, exportSchema = false )
public abstract class MyABookDB extends RoomDatabase {
    public abstract MyABookDAO getMyABookDao();
}
