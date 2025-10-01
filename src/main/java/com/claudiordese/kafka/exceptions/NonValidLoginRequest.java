package com.claudiordese.kafka.exceptions;

public class NonValidLoginRequest extends RuntimeException {
    public NonValidLoginRequest() {
        super("The user or password is not valid!");
    }
}
