package com.synrgy.binarfud.Binarfud.controller.util;

import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserUtil {

    private final UserService userService;

    public UserUtil(UserService userService) {
        this.userService = userService;
    }

    public boolean createUser(String name, String username, String email, String pass) {
        return userService.insertUserProcedure(name, username, email, pass);
    }

    public void getAllUsers() {
        List<Users> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Belum ada user di database");
        } else {
            users.forEach(user -> System.out.println("username :" + user.getUsername()));
        }
    }

    public Users getUserDetailByUsername(String username) {
        Users user;
        try {
            user = userService.getUserByUsername(username);
            log.info(user.getId() +" | "+ user.getUsername() +" | "+ user.getEmailAddress());
            return user;
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    public Users getUserDetailById(String id) {
        Users user;
        try {
            user = userService.getUserById(id);
            return user;
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    public void getAllUsersByUsernameLike(String s) {
        List<Users> users = userService.getUsersByUsernameLike(s);
        users.forEach(user -> System.out.println(user.getId() +" | "+ user.getUsername() +" | "+ user.getEmailAddress()));
    }

    public void hardDeleteUser(String username) {
        Users user = userService.getUserByUsername(username);
        userService.hardDeleteUser(user);
    }

    public void deleteUser(String id) {
        Users user = userService.getUserById(id);
        user.setDeleted(true);
        userService.softDeleteUser(user);
    }

    public Users updateUser(Users user) {
        try {
            user = userService.updateUserData(user);
            log.debug("User telah berhasil diupdate");
            return user;
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }
}
