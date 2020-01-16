package com.example.passwordstoreapp2;

import android.app.Application;

import net.sqlcipher.database.SQLiteDatabase;

public class ReleaseApp extends Application {

    private static ReleaseApp instance;
    @Override
    public void onCreate(){
        super.onCreate();
        SQLiteDatabase.loadLibs(this);
        instance=this;
    }

    public static ReleaseApp getInstance(){
        return instance;
    }
}
