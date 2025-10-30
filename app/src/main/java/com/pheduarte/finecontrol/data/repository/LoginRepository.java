package com.pheduarte.finecontrol.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.pheduarte.finecontrol.data.local.AppDataBase;
import com.pheduarte.finecontrol.data.local.UserDAO;
import com.pheduarte.finecontrol.data.local.TransactionsDAO;

import com.pheduarte.finecontrol.data.User;

import android.os.Looper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import android.os.Handler;


public class LoginRepository {

    private final UserDAO userDAO;
    private TransactionsDAO transactionsDAO;

    private final ExecutorService executorService;

    public LoginRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance(application);
        userDAO = db.userDAO();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<User> getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public LiveData<String> getFirstName(String email) { return userDAO.getFirstName(email); }

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

    public void deleteUser(String email, Runnable onComplete) {
        executorService.execute(() -> {
            userDAO.deleteUser(email);
            new Handler(Looper.getMainLooper()).post(onComplete);
        });
    }
}
