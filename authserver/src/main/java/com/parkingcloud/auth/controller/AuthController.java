package com.parkingcloud.auth.controller;

import com.parkingcloud.auth.domain.*;
import com.parkingcloud.auth.exception.BadCaptchaException;
import com.parkingcloud.auth.exception.BadTokenException;
import com.parkingcloud.auth.exception.UserExistException;
import com.parkingcloud.auth.service.CaptchaService;
import com.parkingcloud.auth.service.JwtTokenUtil;
import com.parkingcloud.auth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caryc on 2017/4/6.
 */
@RestController
@RequestMapping("/auth")
@Api(value = "Authentication API", description = "Authentication API")
public class AuthController {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @RequestMapping(value = "/captcha",method = RequestMethod.POST)
    @ApiOperation(value = "Get captcha")
    public ResponseEntity<?>  captcha(@RequestBody AuthRequest authRequest) throws AuthenticationException{
        logger.debug("Get captcha.");
        if (authRequest==null || StringUtils.isEmpty(authRequest.getUsername())){
            throw new UsernameNotFoundException("Username was not found");
        }
        String captcha = captchaService.getCaptcha(authRequest.getUsername());
        return ResponseEntity.ok(AuthResponse.success().setData("captcha",captcha));
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ApiOperation(value = "Register user")
    public ResponseEntity<?>  register(@RequestBody AuthRequest authRequest) throws AuthenticationException{
        logger.debug("Register with \r\n"+authRequest.toString());
        if (authRequest==null || StringUtils.isEmpty(authRequest.getUsername())){
            throw new UsernameNotFoundException("Username is empty.");
        }
        String captcha = captchaService.getCachedCaptcha(authRequest.getUsername());
        if (StringUtils.isEmpty(captcha)){
            throw new BadCaptchaException("Captcha is empty");
        }
        if (!StringUtils.pathEquals(captcha,authRequest.getCaptcha())){
            throw new BadCaptchaException("Bad captcha");
        }
        if (StringUtils.isEmpty(authRequest.getPassword())){
            throw new BadCredentialsException("Bad credentials");
        }
        if (userService.isUserExist(authRequest.getUsername())){
            throw new UserExistException("User has existed");
        }
        boolean result = userService.createUser(authRequest.getUsername(),authRequest.getPassword());
        if (result){
            return ResponseEntity.ok(AuthResponse.success());
        } else {
            logger.warn("Create user failed.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AuthResponse.failure("Create user failed"));
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "Login")
    public ResponseEntity<?>  login(@RequestBody AuthRequest authRequest){
        logger.debug("Login with \r\n"+authRequest.toString());
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = tokenUtil.generateToken(userDetails,authRequest);
        return ResponseEntity.ok(AuthResponse.success().setData("token", token));
    }

    private String getUsernameFromToken(String token){
        if (StringUtils.isEmpty(token)){
            throw new BadTokenException("Token is empty");
        }
        if (!tokenUtil.parseToken(token)) {
            throw new BadTokenException("Token parse failed");
        }
        if (tokenUtil.isExpired()) {
            throw new BadTokenException("Token was expired");
        }
        String username = tokenUtil.getUsername();
        if (StringUtils.isEmpty(username)){
            throw new BadTokenException("Username is empty in token");
        }
        return username;
    }

    @RequestMapping(value = "/refresh")
    @ApiOperation(value = "Refresh token")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request,@RequestBody AuthRequest authRequest) {
        String token = request.getHeader(tokenHeader);
        String username = getUsernameFromToken(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String newToken = tokenUtil.generateToken(userDetails,authRequest);
        return ResponseEntity.ok(AuthResponse.success().setData("token",newToken));
    }

    @RequestMapping(value = "/reset")
    @ApiOperation(value = "Reset password")
    public ResponseEntity<?> reset(HttpServletRequest request,@RequestBody AuthRequest authRequest) {
        String token = request.getHeader(tokenHeader);
        String username = getUsernameFromToken(token);
        if (userService.isUserExist(username)){
            throw new UserExistException("User has existed");
        }
        boolean result = userService.resetUserPassword(authRequest.getUsername(),authRequest.getPassword());
        if (result){
            return ResponseEntity.ok(AuthResponse.success());
        } else {
            logger.warn("Create user failed.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AuthResponse.failure("Create user failed"));
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ApiOperation(value = "Logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
       // 1. App can clear the local token to logout the system
        // 2. Server can keep the token in Redis, then remove it when get logout request, this need server
        // side keep the "session" token.,
        return ResponseEntity.ok(AuthResponse.success());
    }
}
