package ru.netology.spring.security.Diploma.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class AppError extends Throwable {
    private int status;
    private String message;
    private Date timestamp;

    public AppError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
