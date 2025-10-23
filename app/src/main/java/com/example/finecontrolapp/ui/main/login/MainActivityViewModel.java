package com.example.finecontrolapp.ui.main.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finecontrolapp.ui.main.data.User;
import com.example.finecontrolapp.ui.main.data.repository.LoginRepository;
import com.example.finecontrolapp.ui.main.data.model.LoggedInUser;
import com.example.finecontrolapp.ui.main.data.repository.TransactionsRepository;

import java.util.List;
import java.util.function.Consumer;
import com.example.finecontrolapp.ui.main.data.repository.TransactionsRepository;



public class MainActivityViewModel extends AndroidViewModel {

    private final LoginRepository repository;
    private TransactionsRepository transactionsRepository;
    private LoggedInUser userLoggedIn;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new LoginRepository(application);
        transactionsRepository = new TransactionsRepository(application);
    }

    public LiveData<String> getFirstName(String email) {
        return repository.getFirstName(email);
    }

    public LiveData<Double> getTotalAmount(String email) {
        return transactionsRepository.getTotalAmount(email);
    }
}




