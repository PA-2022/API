package com.example.demo;

import java.util.List;

public class UserService {
    UserStore userStore;
    UserConfig userConfig;

    public UserService(UserStore userStore, UserConfig userConfig) {
        this.userStore = userStore;
        this.userConfig = userConfig;
    }

    List<User> getUsers() {
        return userStore.getUsers();
    }

    public List<User> create(User user) {
        if(userStore.count() < userConfig.maxUsers ) {
            userStore.store(user);
        } else {
            System.out.println("User can't be created");
            throw new IllegalStateException("User can't be created");
        }
        return userStore.users;
    }
}
