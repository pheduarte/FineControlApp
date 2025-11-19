package com.pheduarte.finecontrol.ui.main;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pheduarte.finecontrol.data.Transactions;
import com.pheduarte.finecontrol.data.repository.TransactionsRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransactionsViewModel extends AndroidViewModel {

    private final TransactionsRepository repository;

    public TransactionsViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionsRepository(application);
    }

    public LiveData<List<Transactions>> getTransactionsByUser(String userEmail) {
        return repository.getTransactionsByUser(userEmail);
    }

    LiveData<List<Transactions>> filterByType(String email, String type) {
        return repository.filterByType(email, type);
    };

    public void insert(Transactions transactions) {
        repository.insert(transactions);
    }

    public void deleteTransactions(int id) {
        repository.deleteTransactions(id);
    }

    public void deleteAllTransactionsByUser(String email) {
        repository.deleteAllTransactionsByUser(email);
    }

    public LiveData<Double> getMonthlyTotal(String userEmail, Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        String monthYear = format.format(calendar.getTime());
        return repository.getMonthlyTotal(userEmail, monthYear);
    };

    public LiveData<Double> getMonthlyByCategory(String userEmail, Calendar calendar, String category) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        String monthYear = format.format(calendar.getTime());
        return repository.getMonthlyByCategory(userEmail, monthYear, category);
    };

    public LiveData<List<Transactions>> searchTransactions(String userEmail, String search) {
        return repository.searchTransactions(userEmail, search);
    };
}
