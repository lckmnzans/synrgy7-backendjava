package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Users;

import java.util.List;

public interface UserService {
    boolean insertUserProcedure(String name, String username, String emailAddress, String password);

    List<Users> getAllUsers();

    Users getUserByUsername(String username);

    Users getUserById(String id);

    Users getUserByEmail(String email);

    List<Users> getUsersByUsernameLike(String s);

    void hardDeleteUser(Users user);

    void softDeleteUser(Users user);

    Users updateUserData(Users user);

    void createUserPostLogin(String username, String email);

}
