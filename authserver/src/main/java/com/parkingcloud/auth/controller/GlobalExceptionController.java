package com.parkingcloud.auth.controller;

import com.parkingcloud.auth.domain.AuthResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caryc on 2017/4/6.
 */
@ControllerAdvice
public class GlobalExceptionController {
    private final Log logger = LogFactory.getLog(this.getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<?> defaultExceptionHandler(HttpServletRequest req, Exception e)  {
        logger.debug("Exception:"+e.toString(),e);
        return ResponseEntity.badRequest().body(AuthResponse.exception(e));
    }
}
