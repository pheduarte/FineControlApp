package com.example.finecontrolapp.ui.main.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String email;
    private String displayName;
    public boolean isLoggedIn;

    public LoggedInUser(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
    }

    public boolean isLoggedIn() {
        return true;
    }

    public String getUserId() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }
}