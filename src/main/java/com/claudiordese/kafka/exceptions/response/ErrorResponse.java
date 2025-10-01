package com.claudiordese.kafka.exceptions.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String message;
}
