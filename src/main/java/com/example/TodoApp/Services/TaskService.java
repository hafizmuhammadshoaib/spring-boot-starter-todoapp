package com.example.TodoApp.Services;

import com.example.TodoApp.Entities.Task;
import com.example.TodoApp.Entities.User;
import com.example.TodoApp.Exceptions.UserNotFoundException;
import com.example.TodoApp.Repository.TaskRepository;
import com.example.TodoApp.Repository.UserRepository;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.*;

@Service
public class TaskService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;


    public Task createTask(int userid, Task task) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userid);
        if (!userOptional.isPresent())
            throw new UserNotFoundException("User Not Found");

        User user = userOptional.get();
        task.setUser(user);
        taskRepository.save(task);
        return taskRepository.save(task);
    }

    public Map<String, Object> getTasks(int userId, int page, int size, String search) throws UserNotFoundException {
        Page<Task> taskPage = taskRepository.findByUserId(userId, PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "todo")));
        Map<String, Object> response = new HashMap<>();
        response.put("tasks", taskPage.getContent());
        response.put("total", taskPage.getTotalElements());
        response.put("pageSize", taskPage.getSize());
        response.put("totalPages", taskPage.getTotalPages());
        response.put("currentPage", page);
        return response;
    }
}
