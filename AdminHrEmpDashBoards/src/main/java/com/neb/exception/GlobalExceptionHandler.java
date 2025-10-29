package com.neb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

/**
 * ---------------------------------------------------------------
 * File Name   : GlobalExceptionHandler.java
 * Package     : com.neb.exception
 * ---------------------------------------------------------------
 * Purpose :
 *   This class provides centralized exception handling for the
 *   entire application using Spring’s @ControllerAdvice feature.
 *
 * Description :
 *   - It catches exceptions thrown from any controller and returns
 *     well-structured error responses to the client.
 *   - It improves error management by avoiding repetitive try-catch
 *     blocks across controllers.
 *
 * Handled Exceptions :
 *   1. CustomeException :
 *        → Handles custom business exceptions.
 *        → Returns HTTP 400 (Bad Request) with a descriptive message.
 *
 *   2. RuntimeException :
 *        → Handles unexpected runtime errors.
 *        → Returns HTTP 500 (Internal Server Error) with details.
 *
 * Methods :
 *   handleCustomException(CustomeException ex, WebRequest request)
 *       → Creates and returns an ErrorResponse for custom exceptions.
 *
 *   handleRuntimeException(RuntimeException ex, WebRequest request)
 *       → Creates and returns an ErrorResponse for general runtime errors.
 *
 * Result :
 *   This class ensures that all application errors are handled
 *   in a consistent, informative, and user-friendly manner.
 * ---------------------------------------------------------------
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomeException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomeException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

