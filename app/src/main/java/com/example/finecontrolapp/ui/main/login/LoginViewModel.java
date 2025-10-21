package com.example.finecontrolapp.ui.main.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finecontrolapp.ui.main.data.User;
import com.example.finecontrolapp.ui.main.data.repository.LoginRepository;

import java.util.List;
import java.util.function.Consumer;

public class LoginViewModel extends AndroidViewModel {

    private final LoginRepository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new LoginRepository(application);
    }

    public LiveData<User> getUser(String email) {
        return repository.getUserByEmail(email);
    }

    public void register(User user) {
        repository.insert(user);
    }

    public LiveData<List<User>> getAllUsers() {
        return repository.getAllUsers();
    }

    public void verifyUser(String email, String password, Consumer<Boolean> callback) {
        repository.verifyCredentials(email, password, callback);
    }

}




