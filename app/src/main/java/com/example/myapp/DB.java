package com.example.myapp;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.View;

import androidx.room.*;
import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class,WorkOuts.class},version = 1,exportSchema = true)
public abstract class DB extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 5;
    public static final ExecutorService dataBaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract UserDao userDao();
    public abstract WorkOutDao workOutDao();

    private static volatile DB INSTANCE;

    public static DB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, DB.class, "myDB").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }



}
