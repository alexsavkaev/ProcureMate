package ru.aston.demo.suppliers.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.demo.suppliers.controller.ProductController;
import ru.aston.demo.suppliers.exception.AppError;
import ru.aston.demo.suppliers.exception.ResourceNotFoundException;

import java.time.LocalDateTime;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);

        return new ResponseEntity<>(new AppError(e.getMessage(), LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }
}
