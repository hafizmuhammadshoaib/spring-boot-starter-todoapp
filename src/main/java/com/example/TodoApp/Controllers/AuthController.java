package com.example.TodoApp.Controllers;

import com.example.TodoApp.DTOs.SignInReqDTO;
import com.example.TodoApp.DTOs.SignInResDTO;
import com.example.TodoApp.DTOs.UserMsDTO;
import com.example.TodoApp.Entities.User;
import com.example.TodoApp.Exceptions.UniqueConstraintException;
import com.example.TodoApp.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/auth")
@Validated
public class AuthController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping(value = "/signup")
    public UserMsDTO createUser(@Valid @RequestBody User user) {
        try {
            return userService.createUser(user);
        } catch (UniqueConstraintException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }

    }
    @PostMapping(value = "/signin")
    public SignInResDTO signInUser(@Valid @RequestBody SignInReqDTO user) {
            return userService.sigInUser(user);
    }
}
