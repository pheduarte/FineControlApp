package com.example.finecontrolapp.ui.main.login;

import androidx.lifecycle.ViewModel;

import com.example.finecontrolapp.databinding.FragmentLoginScreenBinding;
import com.example.finecontrolapp.ui.main.data.User;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finecontrolapp.ui.main.data.User;
import com.example.finecontrolapp.ui.main.data.repository.LoginRepository;

import java.util.List;

import kotlinx.coroutines.CoroutineScope;

public class LoginViewModel extends ViewModel {

    private final LoginRepository repository;

    public LoginViewModel(Application application) {
        super((CoroutineScope) application);
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
}