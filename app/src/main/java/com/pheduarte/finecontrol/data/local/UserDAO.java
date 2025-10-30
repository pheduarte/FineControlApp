package com.pheduarte.finecontrol.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pheduarte.finecontrol.data.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    LiveData<User> getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserNow(String email);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT fName FROM users WHERE email = :email LIMIT 1")
    LiveData<String> getFirstName(String email);

    @Query("DELETE FROM users WHERE email = :email")
    void deleteUser(String email);
}
