package com.example.finecontrolapp.ui.main.data;

public class User {
    private String fName;
    private String lName;
    private String email;
    private int phoneNumber;
    private String password;
    private int userID;

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
