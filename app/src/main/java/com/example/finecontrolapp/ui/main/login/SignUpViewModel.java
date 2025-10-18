package com.example.finecontrolapp.ui.main.login;

import androidx.lifecycle.ViewModel;
import com.example.finecontrolapp.ui.main.data.User;

public class SignUpViewModel extends ViewModel {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int phoneNumber;

    public void setUserDetails(String fName, String lName, String email, int phone, String pass) {
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.phoneNumber = phone;
        this.password = pass;
    }

    public User getUser() {
        return new User(firstName, lastName, email, phoneNumber, password);
    }
}
