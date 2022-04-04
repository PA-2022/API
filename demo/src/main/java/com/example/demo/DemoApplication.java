package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        UserService userService = new UserService(new UserStore(new ArrayList<>()), new UserConfig(2));

        userService.create(new User("user1", "pswd1"));
        userService.create(new User("user2", "psw2"));
        userService.create(new User("user3", "psw3"));
        SpringApplication.run(DemoApplication.class, args);
    }

}
