package com.parkingcloud.auth.service;

import com.parkingcloud.auth.exception.UnauthorizedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by caryc on 2017/4/6.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        throw new UnauthorizedException("Unauthorized");
        // This method is invoked when user tries to access a secured REST resource without supplying any credentials
        // We just send a 401 Unauthorized response since we have no 'login page' to redirect to
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
