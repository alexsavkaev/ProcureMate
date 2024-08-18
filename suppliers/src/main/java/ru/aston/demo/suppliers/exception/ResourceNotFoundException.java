package ru.aston.demo.suppliers.exception;

import java.util.NoSuchElementException;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
