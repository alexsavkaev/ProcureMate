package ru.aston.demo.orders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(OrderCreationException.class)
  public ResponseEntity<String> handleOrderCreationException(OrderCreationException e) {
    // Обрабатываем исключение
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка создания заказа");
  }
}