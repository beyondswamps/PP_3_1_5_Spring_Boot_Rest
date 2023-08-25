package ru.kata.spring.boot_security.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler
    public ResponseEntity<ErrorEntity> handleException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>(new ErrorEntity(userNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException methodArgumentNotValidException, BindingResult bindingResult) {
        Map<String, String> errorsMap = bindingResult
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorEntity> handleException(WrongPasswordException wrongPasswordException) {
        return new ResponseEntity<>(new ErrorEntity(wrongPasswordException.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
