package com.example.finecontrolapp.ui.main.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.finecontrolapp.ui.main.data.local.AppDataBase;
import com.example.finecontrolapp.ui.main.data.local.UserDAO;
import com.example.finecontrolapp.ui.main.data.User;
import android.content.Context;
import android.os.Looper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import android.os.Handler;
import android.os.Looper;



public class LoginRepository {

    private final UserDAO userDAO;
    private final ExecutorService executorService;

    public LoginRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance(application);
        userDAO = db.userDAO();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<User> getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public LiveData<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void insert(User user) {
        executorService.execute(() -> userDAO.insert(user));
    }

    public void verifyCredentials(String email, String password, Consumer<Boolean> callback) {
        executorService.execute(() -> {
            User user = userDAO.getUserNow(email);
            boolean isValid = (user != null && user.password.equals(password));

            new Handler(Looper.getMainLooper()).post(() -> callback.accept(isValid));
        });
    }


}
