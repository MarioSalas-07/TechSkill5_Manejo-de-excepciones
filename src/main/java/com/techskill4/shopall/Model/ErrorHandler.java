package com.techskill4.shopall.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ErrorHandler {
    private int status;
    private String message;
    private long timestamp;

    public ErrorHandler(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
