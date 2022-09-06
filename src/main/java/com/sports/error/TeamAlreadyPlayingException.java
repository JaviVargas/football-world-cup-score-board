package com.sports.error;

public class TeamAlreadyPlayingException extends RuntimeException {

    public TeamAlreadyPlayingException(String message) {
        super(message);
    }
}
