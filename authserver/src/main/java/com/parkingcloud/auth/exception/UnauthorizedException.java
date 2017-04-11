package com.parkingcloud.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by caryc on 2017/4/9.
 */
public class UnauthorizedException extends AuthenticationException{

    public UnauthorizedException(String msg, Throwable t) {
        super(msg, t);
    }

    public UnauthorizedException(String msg) {
        super(msg);
    }
}
