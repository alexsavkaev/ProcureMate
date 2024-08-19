package ru.aston.demo.suppliers.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppError {
    private String message;
    private LocalDateTime timestamp;
}
