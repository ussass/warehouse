package ru.trofimov.warehouse.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<EntityNotFoundException> getNonExistentEntity(HttpServletRequest request, NoSuchElementException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        EntityNotFoundException exception = new EntityNotFoundException(
                status,
                e.getMessage(),
                "No data with this id number",
                request.getRequestURI());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    ResponseEntity<EntityNotFoundException> deleteNonExistentEntity (HttpServletRequest request, EmptyResultDataAccessException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String[] messageArr = e.getMessage().split(" ");
        String message = messageArr[0] + " " + Arrays.stream(messageArr).skip(3).collect(Collectors.joining(" "));

        EntityNotFoundException exception = new EntityNotFoundException(
                status,
                message,
                "No data with this id number",
                request.getRequestURI());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
}
