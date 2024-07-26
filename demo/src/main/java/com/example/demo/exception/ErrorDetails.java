package com.example.demo.exception;

import lombok.Data;

import java.util.Date;
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String errorCode;
    private String details;

    public ErrorDetails(Date timestamp, String message, String errorCode, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.errorCode = errorCode;
        this.details = details;
    }


}
