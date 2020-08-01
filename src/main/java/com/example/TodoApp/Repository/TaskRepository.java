package com.example.TodoApp.Repository;

import com.example.TodoApp.Entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer> {
}
