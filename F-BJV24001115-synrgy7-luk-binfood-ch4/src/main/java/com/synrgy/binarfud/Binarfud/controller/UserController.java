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

    public void test() {
        createUser("Lukman Sanusi", "lckmnzans", "lckmnzans@gmail.com", "123");
        createUser("Sou Hayakawa","sommthe", "sothes@mail.com", "110345");
    }

    public void createUser(String name, String username, String email, String pass) {
        userService.insertUserProcedure(name, username, email, pass);
    }

    public void showAllUsers() {
        List<Users> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Belum ada user di database");
        } else {
            users.forEach(user -> System.out.println("username :" + user.getUsername()));
        }
    }

    public void showUserDetailByUsername(String username) {
        Users user;
        try {
            user = userService.getUserByUsername(username);
            System.out.println(user.getId() +" | "+ user.getUsername() +" | "+ user.getEmailAddress());
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public void showUsersDetailByUsernameLike(String s) {
        List<Users> users = userService.getUsersByUsernameLike(s);
        users.forEach(user -> System.out.println(user.getId() +" | "+ user.getUsername() +" | "+ user.getEmailAddress()));
    }

    public void deleteUser(String username) {
        Users user = userService.getUserByUsername(username);
        userService.hardDeleteUser(user);
    }

    public void updateUser(String username, String newUsername) {
        Users user;
        try {
            user = userService.getUserByUsername(username);
            userService.updateUserData(user, newUsername);
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
            System.out.println(username + " gagal diupdate");
        }

    }
}
