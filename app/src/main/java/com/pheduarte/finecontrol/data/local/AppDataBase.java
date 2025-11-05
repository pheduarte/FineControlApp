package com.pheduarte.finecontrol.data.local;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.pheduarte.finecontrol.data.Transactions;
import com.pheduarte.finecontrol.data.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Transactions.class}, version = 3, exportSchema = true)

public abstract class AppDataBase extends RoomDatabase{
    private static volatile AppDataBase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract UserDAO userDAO();
    public abstract TransactionsDAO transactionDAO();

    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDataBase.class, "finecontrol_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
