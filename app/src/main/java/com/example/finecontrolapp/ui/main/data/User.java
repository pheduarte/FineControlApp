package com.example.finecontrolapp.ui.main.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")

public class User {

    @PrimaryKey(autoGenerate = true)
    public int userID;

    public String fName;
    public String lName;
    public String email;
    public int phoneNumber;
    public String password;

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
