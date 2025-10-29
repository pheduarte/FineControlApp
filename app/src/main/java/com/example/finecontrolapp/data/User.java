package com.example.finecontrolapp.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")

public class User {

    @PrimaryKey
    @NonNull
    public String email;

    public String fName;
    public String lName;
    public int phoneNumber;
    public String password;
    public int userID;

    public User(String fName, String lName, String email, int phoneNumber, String password) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;

        this.userID = createUserId();
    }

    private int createUserId () {
        return (int)(Math.random() * 1000000000);
    }
}
