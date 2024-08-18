package com.geist_chamber.geist_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<RestError> handleAuthenticationException(Exception ex) {

        RestError re = new RestError(HttpStatus.UNAUTHORIZED,
                "Authentication failed at controller advice");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    @ResponseBody
    public ResponseEntity<RestError> handleIllegalArgumentException(IllegalArgumentException ex) {
        RestError re = new RestError(HttpStatus.BAD_REQUEST, "Invalid argument: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re);
    }

    @ExceptionHandler({ RuntimeException.class })
    @ResponseBody
    public ResponseEntity<String> handleGenericException(RuntimeException ex) {
        RestError re = new RestError(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(re.getLocalizedMessage());
    }
    @ExceptionHandler({ RestError.class })
    @ResponseBody
    public ResponseEntity<?> handleGenericException(RestError ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.entry("msg",ex.getErrorMessage()));
    }

}
