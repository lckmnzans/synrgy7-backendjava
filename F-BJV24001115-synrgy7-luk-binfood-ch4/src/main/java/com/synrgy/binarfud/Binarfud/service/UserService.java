package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Users;

import java.util.List;

public interface UserService {
    void insertUserProcedure(String username, String emailAddress, String password);

    List<Users> getAllUsers();

    Users getUserByUsername(String username);

    List<Users> getUsersByUsernameLike(String s);
}
