package com.parkingcloud.auth.domain;

import com.parkingcloud.auth.exception.BadCaptchaException;
import com.parkingcloud.auth.exception.SmsProviderException;
import com.parkingcloud.auth.exception.UnauthorizedException;
import com.parkingcloud.auth.exception.UserExistException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by caryc on 2017/4/6.
 */
public class AuthResponse extends ApiResponse {
    private static final String SERVICE_NAME = "Auth";
    public static final int CodeAuthenticationFailed= -1000;
    public static final int CodeUsernameNotFound = -1001;
    public static final int CodeBadCredentials = -1002;
    public static final int CodeDisabled = -1003;
    public static final int CodeLocked= -1004;
    public static final int CodeCredentialsExpired= -1005;
    public static final int CodeBadCaptcha = -1006;
    public static final int CodeSmsProvide = -1007;
    public static final int CodeUserExist = -1008;
    public static final int CodeUnauthorized = -1009;

    protected AuthResponse(){
        super();
        this.setService(SERVICE_NAME);
    }

    protected AuthResponse(int errorCode,String errorMessage){
        super(errorCode,errorMessage);
        this.setService(SERVICE_NAME);
    }

    protected AuthResponse(Exception e){
        super(e);
        this.setService(SERVICE_NAME);
        if (e instanceof AuthenticationException) {
            if (e instanceof UsernameNotFoundException) {
                this.setErrorCode(CodeUsernameNotFound);
            } else if (e instanceof DisabledException) {
                this.setErrorCode(CodeDisabled);
            } else if (e instanceof LockedException) {
                this.setErrorCode(CodeLocked);
            } else if (e instanceof BadCredentialsException) {
                this.setErrorCode(CodeBadCredentials);
            } else if (e instanceof CredentialsExpiredException) {
                this.setErrorCode(CodeCredentialsExpired);
            } else if (e instanceof BadCaptchaException) {
                this.setErrorCode(CodeBadCaptcha);
            } else if (e instanceof SmsProviderException){
                this.setErrorCode(CodeSmsProvide);
            } else if (e instanceof UserExistException){
                this.setErrorCode(CodeSmsProvide);
            } else if (e instanceof UnauthorizedException){
                this.setErrorCode(CodeUnauthorized);
            } else {
                this.setErrorCode(CodeAuthenticationFailed);
            }
        }
    }

    public static AuthResponse success(){
        return new AuthResponse(ApiResponse.CodeSuccess,"");
    }

    public static AuthResponse failure(String errorMessage){
        return new AuthResponse(ApiResponse.CodeFailure,errorMessage);
    }

    public static AuthResponse exception(Exception e){
        return new AuthResponse(e);
    }

}
