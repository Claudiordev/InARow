package com.claudiordese.kafka.exceptions;

public class LoggedInException extends RuntimeException {
    public LoggedInException(String username){
        super("Player with username " + username + " is logged in.");
    }
}
