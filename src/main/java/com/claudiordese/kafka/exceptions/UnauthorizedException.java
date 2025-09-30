package com.claudiordese.kafka.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String username){
        super("Player with username " + username + " is not logged in.");
    }
}
