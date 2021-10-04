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
    ResponseEntity<EntityNotFoundException> handleException(HttpServletRequest request, NoSuchElementException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        EntityNotFoundException exception = new EntityNotFoundException(
                status,
                e.getMessage(),
                "No data with this id number",
                request.getRequestURI());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
}
