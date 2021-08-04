package io.applianceskeeper.configuration;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;


@ControllerAdvice
@Slf4j
class GlobalExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    ResponseEntity<Set<String>> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Set<String> messages = constraintViolations.stream()
                .map(constraintViolation -> String.format("wrong input on key: '%s', value: '%s', message: %s",
                        constraintViolation.getPropertyPath(), constraintViolation.getInvalidValue(),
                        constraintViolation.getMessage()))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidFormatException.class})
    ResponseEntity<String> handleDeserializeException(InvalidFormatException e) {
        var message = String.format("Wrong input on field: '%s', message: %s", e.getPathReference(),
                e.getOriginalMessage());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    ResponseEntity<String> handleWrongPathVariable(MethodArgumentTypeMismatchException e, WebRequest request) {

        var message = String.format("Wrong input on path variable: '%s', message: %s, %s", e.getName(),
                e.getMessage(), request.getContextPath());
        e.printStackTrace();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
