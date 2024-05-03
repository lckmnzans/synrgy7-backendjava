package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.repository.UsersRepository;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UsersRepository usersRepository;

    @Override
    public void insertUserProcedure(String username, String emailAddress, String password) {
        usersRepository.insertUserData(username, emailAddress, password);
        log.info("User Data successfully created");
    }

    @Override
    public List<Users> getAllUsers() {
        List<Users> usersList = usersRepository.findAll();
        if (usersList.isEmpty()) {
            return Collections.emptyList();
        }
        return usersList;
    }

    @Override
    public Users getUserByUsername(String username) {
        Optional<Users> user = usersRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new RuntimeException("data with username \""+username+"\" does not exist");
        };
        return user.get();
    }

    @Override
    public List<Users> getUsersByUsernameLike(String s) {
        List<Users> users = usersRepository.findByUsernameLike("%"+s+"%");
        if (users.isEmpty()) {
            throw new RuntimeException();
        }
        return users;
    }

    @Override
    public void hardDeleteUser(Users user) {
        usersRepository.delete(user);
    }

    @Override
    public Users updateUserData(Users user, String newUsername) {
        user.setUsername(newUsername);
        usersRepository.save(user);
        return user;
    }
}
