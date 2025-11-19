package com.pheduarte.finecontrol.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.pheduarte.finecontrol.data.Transactions;
import java.util.List;

@Dao
public interface TransactionsDAO {

    @Insert
    void insert(Transactions transaction);

    @Query("SELECT * FROM transactions WHERE userEmail = :userEmail ORDER BY date")
    LiveData<List<Transactions>> getTransactionsByUser(String userEmail);

    @Query("SELECT * FROM transactions WHERE userEmail = :email AND type = :type")
    LiveData<List<Transactions>> filterByType(String email, String type);

    @Query("DELETE FROM transactions WHERE transactionId = :id")
    void deleteTransaction(int id);

    @Query("SELECT SUM(CASE " +
            " WHEN type = 'Expense' THEN -amount " +
            " ELSE amount END) " +
            " FROM transactions WHERE userEmail = :userEmail")
    LiveData<Double> getTotalAmount(String userEmail);

    @Query("DELETE FROM transactions WHERE userEmail = :userEmail")
    void deleteAllTransactionsForUser(String userEmail);

    @Query("DELETE FROM transactions")
    void deleteAll();

    @Query("SELECT SUM(amount) " +
            "FROM transactions " +
            "WHERE userEmail = :userEmail " +
            "AND type = 'Expense' " +
            "AND strftime('%Y-%m', date) = :monthYear")
    LiveData<Double> getMonthlyTotal(String userEmail, String monthYear);

    @Query("SELECT SUM(CASE WHEN type = 'Expense' THEN -amount ELSE amount END) " +
            "FROM transactions " +
            "WHERE userEmail = :userEmail " +
            "AND category = :category " +
            "AND strftime('%Y-%m', date) = :monthYear")
    LiveData<Double> getMonthlyByCategory(String userEmail, String monthYear, String category);

    @Query("SELECT COUNT(*) FROM transactions WHERE userEmail = :userEmail")
    LiveData<Integer> getCount(String userEmail);

    @Query("SELECT * FROM transactions WHERE userEmail = :userEmail AND (category LIKE '%' || :search || '%' OR description LIKE '%' || :search || '%' OR type LIKE '%' || :search || '%') ORDER BY date DESC")
    LiveData<List<Transactions>> searchTransactions(String userEmail, String search);

}
