package com.example.passwordstoreapp2;

import com.example.passwordstoreapp2.DaggerPasswordComponent;

import java.util.List;

import io.reactivex.Single;

public class Repository {
    PasswordComponent component= DaggerPasswordComponent.builder()
            .passwordDaoModule(new PasswordDaoModule(ReleaseApp.getInstance().getApplicationContext()))
            .build();
    PasswordDao dao=component.getDao();

    public Single<Long> insert(Password password){
        return Single.create(emitter -> {
            emitter.onSuccess(dao.insert(password));
        });
    }

    public Single<Integer> update(Password password){
        return Single.create(emitter -> {
            emitter.onSuccess(dao.update(password));
        });
    }

    public Single<Integer> delete(Password password){
        return Single.create(emitter -> {
            emitter.onSuccess(dao.delete(password));
        });
    }

    public Single<List<Password>> getAll(){
        return Single.create(emitter -> {
            emitter.onSuccess(dao.getAll());
        });
    }
}
