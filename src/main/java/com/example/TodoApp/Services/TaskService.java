package com.example.TodoApp.Services;

import com.example.TodoApp.Entities.Task;
import com.example.TodoApp.Entities.User;
import com.example.TodoApp.Exceptions.UserNotFoundException;
import com.example.TodoApp.Repository.TaskRepository;
import com.example.TodoApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;


    public Task createTask(int userid,Task task) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userid);
        if (!userOptional.isPresent())
            throw new UserNotFoundException("User Not Found");

        User user = userOptional.get();
        task.setUser(user);
        taskRepository.save(task);
        return taskRepository.save(task);
    }
    public List<Task> getTasks(int userId)throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent())
            throw new UserNotFoundException("User Not Found");

        return userOptional.get().getTasks();

    }
}
