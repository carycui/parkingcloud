package com.parkingcloud.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by caryc on 2017/4/6.
 */
public class SmsProviderException extends AuthenticationException {
    public SmsProviderException(String msg) {
        super(msg);
    }
    public SmsProviderException(String msg, Throwable t) {
        super(msg, t);
    }
}
