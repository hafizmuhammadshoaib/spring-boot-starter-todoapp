package com.example.TodoApp.Services;

import com.example.TodoApp.Entities.Task;
import com.example.TodoApp.Entities.User;
import com.example.TodoApp.Exceptions.UserNotFoundException;
import com.example.TodoApp.Repository.TaskRepository;
import com.example.TodoApp.Repository.UserRepository;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent())
            throw new UserNotFoundException("User Not Found");
        List<Task> tasks = userOptional.get().getTasks();
        if (search.length() > 0) {
            List<Task> searchedList = new ArrayList<>();
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getTodo().equals(search)) {
                    searchedList.add(tasks.get(i));
                    break;
                }
            }
            tasks = searchedList;
        }

        MutableSortDefinition mutableSortDefinition = new MutableSortDefinition("id", true, false);
        PagedListHolder<Task> pagedListHolder = new PagedListHolder<Task>(tasks);
        pagedListHolder.setSort(mutableSortDefinition);
        pagedListHolder.resort();
        pagedListHolder.setPage(page);
        pagedListHolder.setPageSize(size);
        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", pagedListHolder.getPage());
        response.put("pageSize", pagedListHolder.getPageSize());
        response.put("totalPages", pagedListHolder.getPageCount());
        response.put("totalElements", pagedListHolder.getNrOfElements());
        response.put("isLastPage", pagedListHolder.isLastPage());
        response.put("tasks", pagedListHolder.getPageList());


        return response;

    }
}
