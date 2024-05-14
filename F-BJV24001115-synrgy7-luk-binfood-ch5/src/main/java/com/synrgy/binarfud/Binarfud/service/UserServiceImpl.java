package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UsersRepository usersRepository;

    @Override
    public boolean insertUserProcedure(String name, String username, String emailAddress, String password) {
        usersRepository.insertUserData(name, username, emailAddress, password);
        return true;
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
        Optional<Users> user = usersRepository.findByUsernameAndDeleted(username, Boolean.FALSE);
        if (user.isEmpty()) {
            throw new RuntimeException("data with username \""+username+"\" does not exist");
        }
        return user.get();
    }

    @Override
    public Users getUserById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Users> user = usersRepository.findById(uuid);
        if (user.isEmpty()) {
            throw new RuntimeException("data does not exist");
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
        usersRepository.deleteUserData(user.getId());
        log.info("Data with username \""+user.getUsername()+"\" is successfully deleted");
    }

    @Override
    public void softDeleteUser(Users user) {
        usersRepository.save(user);
    }

    @Override
    public Users updateUserData(Users user) {
        Users oldUser = getUserNoException(user.getId());
        if (oldUser != null) {
            if (user.getUsername() != null) oldUser.setUsername(user.getUsername());
            if (user.getEmailAddress() != null) oldUser.setEmailAddress(user.getEmailAddress());
            if (user.getName() != null) oldUser.setName(user.getName());
            if (user.getPassword() != null) oldUser.setPassword(user.getPassword());
            if ((user.getUsername() == null) && (user.getName() == null) && (user.getEmailAddress() == null) && (user.getPassword() == null)) {
                throw new RuntimeException("value null, process cancelled");
            }
        } else {
            throw new RuntimeException("user does not exist");
        }
        usersRepository.save(oldUser);
        log.info("User successfully updated");
        return oldUser;
    }

    private Users getUserNoException(UUID id) {
        Users user;
        try {
            Optional<Users> userOptional = usersRepository.findById(id);
            if (userOptional.isEmpty()) return null;
            user = userOptional.get();
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
        return user;
    }
}
