package com.parkingcloud.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by caryc on 2017/4/10.
 */
public class BadTokenException extends AuthenticationException{
    public BadTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public BadTokenException(String msg) {
        super(msg);
    }
}
