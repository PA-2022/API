package com.example.demo;

public class User {
    String email;
    String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return this.email + ", " + this.password;
    }
}
