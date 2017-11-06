package com.viktor.yurlov.exeption;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String s) {
        super(s);
    }

}
