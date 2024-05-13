package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.payload.Response;
import com.synrgy.binarfud.Binarfud.payload.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
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
            user = userController.showUserDetailByUsername(username);
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
            return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
        }
    }
}
