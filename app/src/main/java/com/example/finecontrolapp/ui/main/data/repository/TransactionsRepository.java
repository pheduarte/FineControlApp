package com.example.finecontrolapp.ui.main.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.finecontrolapp.ui.main.data.Transactions;
import com.example.finecontrolapp.ui.main.data.local.AppDataBase;
import com.example.finecontrolapp.ui.main.data.local.TransactionsDAO;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionsRepository {
    private final TransactionsDAO transactionsDAO;
    private final ExecutorService executorService;

    public TransactionsRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance(application);
        transactionsDAO = db.transactionDAO();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Transactions transaction) {
        executorService.execute(() -> transactionsDAO.insert(transaction));
    }

    public LiveData<List<Transactions>> getTransactionsByUser(String userEmail) {
        return transactionsDAO.getTransactionsByUser(userEmail);
    }

    public void deleteTransactions(int id) {
        executorService.execute(() -> transactionsDAO.deleteTransaction(id));
    }

    public LiveData<Double> getTotalAmount(String email){
        return transactionsDAO.getTotalAmount(email);
    };

    public void deleteAllTransactionsForUser(String userEmail) {
        executorService.execute(() -> transactionsDAO.deleteAllTransactionsForUser(userEmail));
    }

    public void deleteAllTransactionsByUser(String email) {
        AppDataBase.databaseWriteExecutor.execute(() -> transactionsDAO.deleteAllTransactionsForUser(email));
    }

    public LiveData<Double> getMonthlyTotal(String userEmail, String monthYear) {
        return transactionsDAO.getMonthlyTotal(userEmail, monthYear);
    }

    public LiveData<Double> getMonthlyByCategory(String userEmail, String monthYear, String category) {
        return transactionsDAO.getMonthlyByCategory(userEmail, monthYear, category);
    }

}
