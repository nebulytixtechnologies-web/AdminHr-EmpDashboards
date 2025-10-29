package com.neb.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import lombok.Setter;

/**
 * ---------------------------------------------------------------
 * File Name   : ErrorResponse.java
 * Package     : com.neb.exception
 * ---------------------------------------------------------------
 * Purpose :
 *   This class represents a structured format for returning
 *   error details to the client when exceptions occur.
 *
 * Description :
 *   - It is used to send meaningful and standardized error
 *     responses from the backend to the frontend.
 *   - Each response includes the HTTP status, error message,
 *     and request path for debugging and logging purposes.
 *
 * Fields :
 *   timestamp → The exact time when the error occurred
 *   status    → The HTTP status code (e.g., 404, 500)
 *   error     → The HTTP error type (e.g., "Not Found", "Internal Server Error")
 *   message   → A descriptive error message explaining the cause
 *   path      → The endpoint path where the error occurred
 *
 * Constructors :
 *   ErrorResponse(int statusCode, String errorMessage, String message, String path)
 *       → Initializes the error details and sets the timestamp automatically.
 *
 * Result :
 *   This class helps provide clear, consistent, and detailed
 *   error messages to API clients for better debugging and handling.
 * ---------------------------------------------------------------
 */

@Data
@AllArgsConstructor
@Setter
@Getter
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ErrorResponse(int statusCode, String errorMessage, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = statusCode;
        this.error = errorMessage;
        this.message = message;
        this.path = path;
    }
}

