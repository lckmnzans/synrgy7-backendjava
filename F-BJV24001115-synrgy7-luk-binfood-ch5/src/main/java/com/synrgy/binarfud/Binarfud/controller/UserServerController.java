package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.payload.Response;
import com.synrgy.binarfud.Binarfud.payload.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api")
public class UserServerController {
    final
    ModelMapper modelMapper;

    final
    UserController userController;

    public UserServerController(UserController userController, ModelMapper modelMapper) {
        this.userController = userController;
        this.modelMapper = modelMapper;
    }

    @GetMapping("user/{username}")
    public ResponseEntity<Response> getUser(@PathVariable("username") String username) {
        Users user;
        try {
            user = userController.getUserDetailByUsername(username);
            return ResponseEntity.ok(new Response.Success(modelMapper.map(user, UserDto.class)));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("user")
    public ResponseEntity<Response> add(@RequestBody UserDto userDto) {
        Users user;
        try {
            user = modelMapper.map(userDto, Users.class);
            userController.createUser(user.getName(), user.getUsername(), user.getEmailAddress(), user.getPassword());
            return ResponseEntity.ok(new Response.SuccessNull("user sukses dibuat"));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error(e.getMessage()), HttpStatus.OK);
        }
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") String userId) {
        try {
            userController.deleteUser(userId);
            return ResponseEntity.ok(new Response.SuccessNull("user sukses dihapus"));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
        }
    }

    @PutMapping("user/{id}")
    public ResponseEntity<Response> update(@PathVariable("id") String userId, @RequestBody UserDto userDto) {
        Users user = modelMapper.map(userDto, Users.class);
        user.setId(UUID.fromString(userId));
        try {
            user = userController.updateUser(user);
            return ResponseEntity.ok(new Response.Success(modelMapper.map(user, UserDto.class)));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
        }
    }
}
