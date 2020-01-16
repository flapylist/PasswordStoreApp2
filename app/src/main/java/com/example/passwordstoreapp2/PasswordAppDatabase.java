package com.example.passwordstoreapp2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Password.class,version = 1,exportSchema = false)
public abstract class PasswordAppDatabase extends RoomDatabase {
    public abstract PasswordDao passwordDao();
}
