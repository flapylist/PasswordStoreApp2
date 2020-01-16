package com.example.passwordstoreapp2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Password {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private String login;

    private String password;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
