package com.parkingcloud.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by caryc on 2017/4/6.
 */
public class BadCaptchaException extends AuthenticationException{

    public BadCaptchaException(String msg, Throwable t) {
        super(msg, t);
    }
    public BadCaptchaException(String msg) {
        super(msg);
    }
}
