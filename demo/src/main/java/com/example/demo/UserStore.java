package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class UserStore {
    List<User> users;

    public UserStore(List<User> userList) {
        this.users = userList;
    }

    void store(User user) {
        this.users.add(user);
    }

    List<User> getUsers() {
        return users;
    }

    int count() {
        return this.users.size();
    }
}
