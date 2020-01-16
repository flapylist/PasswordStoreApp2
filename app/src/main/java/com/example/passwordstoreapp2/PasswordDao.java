package com.example.passwordstoreapp2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PasswordDao {

    @Query("SELECT * FROM password ORDER BY name")
    List<Password> getAll();

    @Query("SELECT * FROM password WHERE id = :id")
    LiveData<Password> getByID(long id);

    @Insert
    long insert(Password password);

    @Update
    int update(Password password);

    @Delete
    int delete(Password password);
}
