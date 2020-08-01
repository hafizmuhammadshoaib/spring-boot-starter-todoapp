package com.example.TodoApp.Controllers;

import com.example.TodoApp.Entities.Task;
import com.example.TodoApp.Entities.User;
import com.example.TodoApp.Exceptions.UserNotFoundException;
import com.example.TodoApp.Repository.TaskRepository;
import com.example.TodoApp.Repository.UserRepository;
import com.example.TodoApp.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping(path = "/{userId}")
    public void createOrder(@PathVariable int userId, @RequestBody Task task)  {
        try {
            taskService.createTask(userId, task);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(path = "/{userId}")
    public List<Task> getTasks(@PathVariable int userId) {
        try {
          return taskService.getTasks(userId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
