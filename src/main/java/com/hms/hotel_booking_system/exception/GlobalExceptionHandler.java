package com.hms.hotel_booking_system.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.hms.hotel_booking_system.payload.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ErrorDto> resourceAlreadyExists(ResourceAlreadyExistException ex, WebRequest request) {
        return new ResponseEntity<>(buildErrorDto(ex.getMessage(), request), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> resourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(buildErrorDto(ex.getMessage(), request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorDto> invalidCredentials(InvalidCredentialsException ex, WebRequest request) {
        return new ResponseEntity<>(buildErrorDto(ex.getMessage(), request), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> accessDenied(AccessDeniedException ex, WebRequest request) {
        return new ResponseEntity<>(buildErrorDto("Access Denied: " + ex.getMessage(), request), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorDto> handleExpiredJwtException(TokenExpiredException ex, WebRequest request) {
        return new ResponseEntity<>(buildErrorDto("JWT token has expired", request), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<ErrorDto> handleMalformedJwtException(JWTDecodeException ex, WebRequest request) {
        return new ResponseEntity<>(buildErrorDto("Invalid JWT token", request), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(SignatureVerificationException.class)
    public ResponseEntity<ErrorDto> handleSignatureException(SignatureVerificationException ex, WebRequest request) {
        return new ResponseEntity<>(buildErrorDto("Invalid JWT signature", request), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.put("error", "Server Error");
        errorDetails.put("message", ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ErrorDto buildErrorDto(String message, WebRequest request) {
        return new ErrorDto(message, LocalDateTime.now(), request.getDescription(false));
    }
}
