package com.example.finecontrolapp.ui.main.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.finecontrolapp.ui.main.data.Transactions;
import java.util.List;

@Dao
public interface TransactionsDAO {

    @Insert
    void insert(Transactions transaction);

    @Query("SELECT * FROM transactions WHERE userEmail = :userEmail ORDER BY date DESC")
    LiveData<List<Transactions>> getTransactionsByUser(String userEmail);

    @Query("DELETE FROM transactions WHERE transactionId = :id")
    void deleteTransaction(int id);

    @Query("DELETE FROM transactions WHERE userEmail = :userEmail")
    void deleteAllTransactionsForUser(String userEmail);
}
