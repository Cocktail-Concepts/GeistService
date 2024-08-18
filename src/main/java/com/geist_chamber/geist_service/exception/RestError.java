package com.geist_chamber.geist_service.exception;

import org.springframework.http.HttpStatus;

public class RestError extends RuntimeException{

    HttpStatus errorCode;
    String errorMessage;

    public RestError(HttpStatus errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}