package com.example.finecontrolapp.ui.main;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finecontrolapp.ui.main.data.Transactions;
import com.example.finecontrolapp.ui.main.data.repository.TransactionsRepository;

import java.util.List;

public class TransactionsViewModel extends AndroidViewModel {

    private final TransactionsRepository repository;

    public TransactionsViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionsRepository(application);
    }

    public LiveData<List<Transactions>> getTransactionsByUser(String userEmail) {
        return repository.getTransactionsByUser(userEmail);
    }

    public void insert(Transactions transactions) {
        repository.insert(transactions);
    }

    public void deleteTransactions(int id) {
        repository.deleteTransactions(id);
    }
}
