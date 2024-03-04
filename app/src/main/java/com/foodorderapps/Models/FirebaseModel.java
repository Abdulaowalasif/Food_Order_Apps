package com.foodorderapps.Models;

public class FirebaseModel {
    String username,email;

    public FirebaseModel(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public FirebaseModel() {
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
