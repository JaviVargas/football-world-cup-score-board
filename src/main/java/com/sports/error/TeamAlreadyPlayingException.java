package com.sports.error;

public class TeamAlreadyPlayingException extends RuntimeException {

    public TeamAlreadyPlayingException(String message) {
        super(String.format("Following teams are already playing: %s", message));
    }
}
