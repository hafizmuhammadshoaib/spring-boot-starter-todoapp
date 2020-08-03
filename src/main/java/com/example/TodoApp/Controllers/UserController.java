package com.example.TodoApp.Controllers;

import com.example.TodoApp.DTOs.UserMsDTO;
import com.example.TodoApp.Entities.User;
import com.example.TodoApp.Exceptions.UniqueConstraintException;
import com.example.TodoApp.Exceptions.UserNotFoundException;
import com.example.TodoApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public List<UserMsDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}")
    public UserMsDTO  getUserById(@PathVariable("id") @Min(1) int id) {

        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }


    }

//    @PostMapping
//    public User createUser(@Valid @RequestBody User user) {
//
//        try {
//            return userService.createUser(user);
//        } catch (UniqueConstraintException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
//        }
//
//    }

    @PutMapping(path = "/{id}")
    public User updateUser(@PathVariable("id") int id, @RequestBody User user) {
        try {
            return userService.updateUser(id, user);
        } catch (UserNotFoundException  e) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public Object deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        System.out.println(id);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
