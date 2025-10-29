package com.example.finecontrolapp.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finecontrolapp.data.repository.LoginRepository;
import com.example.finecontrolapp.data.model.LoggedInUser;
import com.example.finecontrolapp.data.repository.TransactionsRepository;


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




