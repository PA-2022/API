package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class UserServiceTest {

    @Test
    public void should_add_one_user() {
        UserService userService = new UserService(new UserStore(new ArrayList<>()), new UserConfig(2));
        userService.create(new User("newMail", "password"));
        var users = userService.getUsers();
        Assertions.assertTrue(users.size() == 1);
    }

    @Test
    public void should_not_add_user() {

        UserService userService = new UserService(new UserStore(new ArrayList<>()), new UserConfig(1));
        userService.create(new User("newMail", "password"));
        userService.create(new User("newMail", "password"));
        var users = userService.getUsers();

        //Assertions.assertThrows(() -> userService.create(new User("newMail", "password")));
    }

}
