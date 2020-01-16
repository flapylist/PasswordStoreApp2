package com.example.passwordstoreapp2;

import android.content.Context;

import androidx.room.Room;



import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PasswordDaoModule {
    private Context context;

    public PasswordDaoModule(Context context){
        this.context=context;
    }

    @Provides
    @Singleton
    public PasswordAppDatabase passwordAppDatabase(){
        return Room.databaseBuilder(context, PasswordAppDatabase.class,"password")
                .build();
    }

    @Provides
    @Singleton
    public PasswordDao passwordDao(PasswordAppDatabase passwordAppDatabase){
        return passwordAppDatabase.passwordDao();
    }

    @Provides
    @Singleton
    public Repository repository(){
        return new Repository();
    }
}
