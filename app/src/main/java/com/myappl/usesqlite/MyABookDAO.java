package com.myappl.usesqlite;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

//
//DBアクセスメソッド定義
//
@Dao
public interface MyABookDAO {

    //全件取得
    @Query( "select * from myabook" )
    List<MyABook> getAll();
    //UserIDをキーに行を取得
    @Query( "select * from myabook where mUserId in (:userIds)" )
    List<MyABook> loadAllByUserIds( int userIds );
    //firstName, lastNameに指定した文字列に含む行を取得
    @Query( "select * from myabook where first_name like :firstName and last_name like :lastName limit 1" )
    List<MyABook> loadAllByName( String firstName, String lastName );

    //行挿入
    @Insert()
    void insertAll( MyABook aBooks );

    //行削除
    @Delete
    void delete( MyABook aBooks );
    //ID指定による削除
    @Query( "delete from myabook where mUserId in (:userId)")
    void deleteByUserId( int userId );

}
