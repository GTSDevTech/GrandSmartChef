package com.grandchefsupreme.exceptions;

public class TokenExpiredException extends UnauthorizedException {

    public TokenExpiredException(String message) {
        super(message);
    }

}