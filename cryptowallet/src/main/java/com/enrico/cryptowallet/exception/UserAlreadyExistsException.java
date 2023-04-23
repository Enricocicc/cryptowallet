package com.enrico.cryptowallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UserAlreadyExistsException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private String email;

    public UserAlreadyExistsException(String email) {
        super(String.format("Email already registered: %s", email));
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
