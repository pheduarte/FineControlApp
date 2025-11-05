package com.pheduarte.finecontrol.login;

import androidx.lifecycle.ViewModel;
import com.pheduarte.finecontrol.data.User;

public class SignUpViewModel extends ViewModel {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;

    public void setUserDetails(String fName, String lName, String email, String phone, String pass) {
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
