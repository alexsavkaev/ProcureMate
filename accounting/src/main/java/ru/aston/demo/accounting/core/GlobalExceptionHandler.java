package ru.aston.demo.accounting.core;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.aston.demo.accounting.dto.ErrorDto;

import java.util.List;

import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.util.CollectionUtils.isEmpty;

@ControllerAdvice
@Slf4j
class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<List<ErrorDto>> onConstraintValidationException(ConstraintViolationException e) {
        List<ErrorDto> errors = e.getConstraintViolations()
                .stream()
                .map(violation -> new ErrorDto(violation.getMessage()))
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler
    ResponseEntity<List<ErrorDto>> onMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        ErrorDto error = new ErrorDto(
                "Parameter %s has invalid type. Required type is %s.".formatted(e.getName(), e.getRequiredType()));

        return ResponseEntity.badRequest().body(List.of(error));
    }

    @ExceptionHandler
    ResponseEntity<Void> onHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        HttpHeaders headers = new HttpHeaders();
        if (!isEmpty(e.getSupportedHttpMethods())) {
            headers.setAllow(e.getSupportedHttpMethods());
        }

        return new ResponseEntity<>(headers, METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    ResponseEntity<List<ErrorDto>> onHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException e) {
        ErrorDto error = new ErrorDto(
                "Media type is not acceptable. Supported media types are " + e.getSupportedMediaTypes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        return new ResponseEntity<>(List.of(error), headers, NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    ResponseEntity<List<ErrorDto>> onNoResourceFoundException(NoResourceFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    ResponseEntity<String> onException(Exception e) {
        log.error("Something went wrong", e);
        return ResponseEntity.internalServerError().build();
    }
}
