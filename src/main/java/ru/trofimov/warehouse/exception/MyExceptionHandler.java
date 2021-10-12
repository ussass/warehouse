package ru.trofimov.warehouse.exception;

import io.jsonwebtoken.SignatureException;
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
        return new ResponseEntity<>(exception, status);
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
        return new ResponseEntity<>(exception, status);
    }

    @ExceptionHandler(InvalidRequestBodyException.class)
    ResponseEntity<EntityNotFoundException> invalidLoginOrPassword (HttpServletRequest request, InvalidRequestBodyException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        EntityNotFoundException exception = new EntityNotFoundException(
                status,
                e.getMessage(),
                "invalid login or password",
                request.getRequestURI());
        return new ResponseEntity<>(exception, status);
    }

    @ExceptionHandler(SignatureException.class)
    ResponseEntity<EntityNotFoundException> invalidToken (HttpServletRequest request, SignatureException e) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        EntityNotFoundException exception = new EntityNotFoundException(
                status,
                e.getMessage(),
                "invalid token",
                request.getRequestURI());
        return new ResponseEntity<>(exception, status);
    }
}
