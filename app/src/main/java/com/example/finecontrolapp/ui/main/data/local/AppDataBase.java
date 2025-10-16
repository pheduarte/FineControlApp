package com.example.finecontrolapp.ui.main.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.finecontrolapp.ui.main.data.User;

@Database(entities = {User.class}, version = 1)

public abstract class AppDataBase extends RoomDatabase{
    private static volatile AppDataBase INSTANCE;

    public abstract UserDAO userDAO();

    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDataBase.class, "finecontrol_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
