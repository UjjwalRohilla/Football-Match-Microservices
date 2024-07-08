package com.test.checkJWT.customExceptions;

public class TokenEmptyException extends RuntimeException{
    public TokenEmptyException() {
        super("Invalid Token or Empty Token");
    }

    public TokenEmptyException(String message) {
        super(message);
    }
}
