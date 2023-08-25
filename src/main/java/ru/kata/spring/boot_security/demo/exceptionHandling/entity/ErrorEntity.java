package ru.kata.spring.boot_security.demo.exceptionHandling.entity;

public class ErrorEntity {
    public String message;

    public ErrorEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
