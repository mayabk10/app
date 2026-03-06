package com.example.myapp;
import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.room.Entity;

@Entity
public class User {
    @Override
    public String toString() {
        return "User{" +
                "name=" + name +
                "email=" + email +
                ",password=" + password + '\'' +
                '}';
    }

    @ColumnInfo(name ="password",defaultValue = "dani")
    public String password;



    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String name;
    @ColumnInfo(name = "email",defaultValue = "aa")
    public String email;


    @Ignore
    public User(){}

    public User(String password, String name,String email){
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return this.name;
    }
}