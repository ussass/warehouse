package ru.trofimov.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<String> handleException(HttpServletRequest request, NoSuchElementException e){
        return new ResponseEntity<>("caught an NoSuchElementException", HttpStatus.OK);
    }
}
