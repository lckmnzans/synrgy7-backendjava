package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    public void init() {
        createUser();
        showAllUsers();
    }

    public void createUser() {
        String name = "Lukman Sanusi";
        String username = "lckmnzans";
        String emailAddress = "lckmnzans@gmail";
        String password = "123";
        userService.insertUserProcedure(name, username, emailAddress, password);
        userService.insertUserProcedure("Sou Hayakawa","sommthe", "sothes@mail.com", "110345");
    }

    public void showAllUsers() {
        List<Users> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Belum ada user di database");
        } else {
            users.forEach(user -> System.out.println("username :" + user.getUsername()));
        }
    }

    public void showUserByUsername(String username) {
        Users user;
        try {
            user = userService.getUserByUsername(username);
            System.out.println(user.getId() +" | "+ user.getUsername() +" | "+ user.getEmailAddress());
        } catch (RuntimeException e) {
            log.warn(e.getLocalizedMessage());
        }
    }

    public void showUsersByUsernameLike(String s) {
        List<Users> users = userService.getUsersByUsernameLike(s);
        users.forEach(user -> System.out.println(user.getId() +" | "+ user.getUsername() +" | "+ user.getEmailAddress()));
    }

    public void deleteUser(String username) {
        Users user = userService.getUserByUsername(username);
        userService.hardDeleteUser(user);
    }

    public void updateUsername(String username, String newUsername) {
        Users user;
        try {
            user = userService.getUserByUsername(username);
            user = userService.updateUserData(user, newUsername);
            System.out.println(username + " berhasil diupdate ke " + user.getUsername());
        } catch (RuntimeException e) {
            log.warn(e.getLocalizedMessage());
            System.out.println(username + " gagal diupdate");
        }

    }
}