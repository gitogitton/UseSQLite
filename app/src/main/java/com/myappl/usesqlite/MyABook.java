package com.myappl.usesqlite;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//
//カラム定義
//
@Entity
public class MyABook {

    @PrimaryKey
    private int mUserId;

    @ColumnInfo( name="first_name" )
    private String mFirstName;

    @ColumnInfo( name="last_name" )
    private String mLastName;

    @ColumnInfo( name="age" )
    private int mAge;

    @ColumnInfo( name="address" )
    private String mAddress;

    //
    //Getter and Setter
    //
    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        this.mUserId = userId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        this.mAge = age;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }
}
