package com.myappl.usesqlite;

import android.arch.persistence.room.Room;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    private boolean create = false;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        if ( create ) {
            createDb();
        }

        final MyABookDAO myABookDAO = new MyABookDAO() {
            @Override
            public List<MyABook> getAll() {
                Log.d( LOG_TAG, "getAll()" );
                MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper( mContext, MySQLiteOpenHelper.DB_NAME, MySQLiteOpenHelper.DB_VERSION );
                //データベースオブジェクトを取得する
                SQLiteDatabase database = dbHelper.getReadableDatabase();
                //
                //SQLiteDatabase.query() の引数↓
                //
                //String table              Table名
                //String[] columns	        Field名 (nullなら全部、と思う)
                //String selection	        WHERE句
                //String[] selectionArgs	WHERE句に?を使用した場合に使用
                //String groupBy	        GROUP BY句
                //String having	            HAVING句
                //String orderBy	        ORDER BY句
                //
                Cursor cursor = database.query( MySQLiteOpenHelper.DB_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null );
                Log.d( LOG_TAG, "cursor count->"+cursor.getCount() );

                cursor.close();
                database.close();

                return null;
            }

            @Override
            public List<MyABook> loadAllByUserIds(int userIds) {
                Log.d( LOG_TAG, "loadAllByUserIds() userID->"+userIds );
                MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper( mContext, MySQLiteOpenHelper.DB_NAME, MySQLiteOpenHelper.DB_VERSION );
                //データベースオブジェクトを取得する
                SQLiteDatabase database = dbHelper.getReadableDatabase();
                //
                //SQLiteDatabase.query() の引数↓
                //
                //String table              Table名
                //String[] columns	        Field名 (nullなら全部、と思う)
                //String selection	        WHERE句
                //String[] selectionArgs	WHERE句に?を使用した場合に使用
                //String groupBy	        GROUP BY句
                //String having	            HAVING句
                //String orderBy	        ORDER BY句
                //
                String[] args = new String[1];
                args[0] = String.valueOf( userIds );
                Cursor cursor = database.query( MySQLiteOpenHelper.DB_NAME,
                        null,
//where句　開始
//      where 句、パターン１，２とも検索結果正常
//　    文字列になるとパターン１のような単純に＋でつなぐと実行時にExceptionが発生する。
//      パターン２のようにwhere句に？を使って、selectionArgs にString配列を使って？に当てはまる値を設定すれば良い。
//where句　パターン１
//                        "user_id="+userIds,
//                        null,
//where句　パターン２
                        "user_id=?",
                        args,
//where句　終わり
                        null,
                        null,
                        null );
                Log.d( LOG_TAG, "cursor count->"+cursor.getCount() );

                cursor.close();
                database.close();

                return null;
            }

            @Override
            public List<MyABook> loadAllByName(String firstName, String lastName) {
                Log.d( LOG_TAG, "loadAllByName() name->"+firstName+" "+lastName );
                MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper( mContext, MySQLiteOpenHelper.DB_NAME, MySQLiteOpenHelper.DB_VERSION );
                //データベースオブジェクトを取得する
                SQLiteDatabase database = dbHelper.getReadableDatabase();
                //
                //SQLiteDatabase.query() の引数↓
                //
                //String table              Table名
                //String[] columns	        Field名 (nullなら全部、と思う)
                //String selection	        WHERE句 --> loadAllByUserIds()のコメント参照！！
                //String[] selectionArgs	WHERE句に?を使用した場合に使用
                //String groupBy	        GROUP BY句
                //String having	            HAVING句
                //String orderBy	        ORDER BY句
                //
                String[] args = new String[2];
                args[0] = firstName;
                args[1] = lastName;
                Cursor cursor = database.query( MySQLiteOpenHelper.DB_NAME,
                        null,
                        "first_name=? and last_name=?",
                        args,
                        null,
                        null,
                        null );
                Log.d( LOG_TAG, "cursor count->"+cursor.getCount() );

                cursor.close();
                database.close();

                return null;
            }

            @Override
            public void insertAll(MyABook aBooks) {
                Log.d( LOG_TAG, "insertAll()->"+aBooks.getFirstName()+" "+aBooks.getLastName() );
                ContentValues contentValues = new ContentValues();
                contentValues.put( "first_name", aBooks.getFirstName() );
                contentValues.put( "last_name", aBooks.getLastName() );
                contentValues.put( "age", aBooks.getAge() );
                contentValues.put( "address", aBooks.getAddress() );

                MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper( mContext, MySQLiteOpenHelper.DB_NAME, MySQLiteOpenHelper.DB_VERSION );
                //データベースオブジェクトを取得する
                SQLiteDatabase database = dbHelper.getReadableDatabase();
                if ( database != null && database.isOpen() ) {
                    long rowId = database.insert( MySQLiteOpenHelper.DB_NAME, null, contentValues );
                    database.close();
                    Log.d( LOG_TAG, "new row ID->"+rowId );
                }
            }

            @Override
            public void delete(MyABook aBooks) {
            }

            @Override
            public void deleteByUserId( int userId ) {
                Log.d( LOG_TAG, "deleteByUserID()" );
                MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper( mContext, MySQLiteOpenHelper.DB_NAME, MySQLiteOpenHelper.DB_VERSION );
                //データベースオブジェクトを取得する
                SQLiteDatabase database = dbHelper.getReadableDatabase();
                String tableName = "myabook";
                String whereClause = "user_id=?";
                String[] whereArgs = new String[1];
                whereArgs[0] = String.valueOf( userId );
                int count = database.delete( tableName, whereClause, whereArgs );
                database.close();

                Log.d( LOG_TAG, "specified ids->"+whereArgs[0] );
                Log.d( LOG_TAG, "count of deleting->"+count );
            }
        };

        //select all -> OK
        List<MyABook> selectedLists = myABookDAO.getAll();

        //select by user ID -> OK
        List<MyABook> selectedByUserId = myABookDAO.loadAllByUserIds( 3 );

        //select by first name and last name -> OK
        List<MyABook> selectedByName = myABookDAO.loadAllByName( "日本", "一郎" );

//        //delete by user id -> OK
//        myABookDAO.deleteByUserId( 3 );

//        //insert -> OK
//        List<MyABook> lists = createColumnData();
//        for ( int i=0; lists!=null && i<lists.size(); i++ ) {
//            myABookDAO.insertAll( lists.get( i ) );
//        }


//        //
//        //arguments: context, class, name of db file
//        //
//        MyABookDB db = Room.databaseBuilder( getApplicationContext(), MyABookDB.class, MySQLiteOpenHelper.DB_NAME )
//                .allowMainThreadQueries() // Main thread でも動作させたい場合 //Disables the main thread query check for Room.
//                .build();
//        //select * from MyABook.
//        List<MyABook> users = db.getMyABookDao().getAll();

    }

    @Override
    protected void onDestroy() {
        Log.d( LOG_TAG, "onDestroy()" );
        MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper( mContext, MySQLiteOpenHelper.DB_NAME, MySQLiteOpenHelper.DB_VERSION );
        //データベースオブジェクトを取得する
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        if ( database!=null && database.isOpen() ) {
            if ( database.isDbLockedByCurrentThread() ) {
                Log.d( LOG_TAG, "Db is Locked By Current Thread" );
            }
            database.close();
        }
        super.onDestroy();
    }

    //
    //private methods
    //
    private void createDb() {
        //データベースヘルパーのインスタンスを作成
        MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper( this, MySQLiteOpenHelper.DB_NAME, MySQLiteOpenHelper.DB_VERSION );
        //データベースオブジェクトを取得する
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //データベースを閉じる
        database.close();
    }

    private List<MyABook> createColumnData() {
        //insert column
        MyABook[] myABooks = new MyABook[2];

        myABooks[0] = new MyABook();
        myABooks[0].setUserId( 1 );
        myABooks[0].setFirstName( "日本" );
        myABooks[0].setLastName( "一郎" );
        myABooks[0].setAge( 11 );
        myABooks[0].setAddress( "日本県西部市東部町南南西01234" );
        myABooks[1] = new MyABook();
        myABooks[1].setUserId( 2 );
        myABooks[1].setFirstName( "米" );
        myABooks[1].setLastName( "二郎" );
        myABooks[1].setAge( 12 );
        myABooks[1].setAddress( "米県東部市西部町北北東５６７８９" );

        List<MyABook> lists = new ArrayList<MyABook>(); //Listはinterface, ArrayListはClass・・・すぐ忘れる。個人的にはArrayList insertListsと定義する方がすっきり出来るが今回はこれで。
        lists.clear(); //clearされてるんだろうけれど・・・
        lists.addAll( Arrays.asList( myABooks ) );
        Log.d( LOG_TAG, "lists count->"+lists.size() );

        return lists;
    }

}
