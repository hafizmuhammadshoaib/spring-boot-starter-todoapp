package com.example.TodoApp.Repository;

import com.example.TodoApp.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Boolean existsByEmail(String email);
}
