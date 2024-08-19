package ru.aston.demo.orders.exception;

import lombok.Getter;

@Getter
public class OrderCreationException extends Exception {
  public OrderCreationException(String message) {
    super(message);
  }

  public OrderCreationException(String message, Throwable cause) {
    super(message, cause);
  }
}
