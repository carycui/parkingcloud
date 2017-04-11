package com.parkingcloud.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by caryc on 2017/4/9.
 */
public class UserExistException extends AuthenticationException {

    public UserExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserExistException(String msg) {
        super(msg);
    }
}
