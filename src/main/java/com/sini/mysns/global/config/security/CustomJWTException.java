package com.sini.mysns.global.config.security;

public class CustomJWTException extends RuntimeException {

    public CustomJWTException(String message)
    {
        super(message);
    }
}
