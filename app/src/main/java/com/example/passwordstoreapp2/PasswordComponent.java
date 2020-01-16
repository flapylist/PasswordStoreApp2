package com.example.passwordstoreapp2;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = PasswordDaoModule.class)
public interface PasswordComponent {
    PasswordDao getDao();
    Repository getRepo();
}
