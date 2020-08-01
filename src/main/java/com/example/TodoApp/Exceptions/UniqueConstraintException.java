package com.example.TodoApp.Exceptions;

public class UniqueConstraintException extends Exception {
    public UniqueConstraintException(String message) {
        super(message);
    }
}
