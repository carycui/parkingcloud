package com.parkingcloud.auth.domain;

import io.swagger.annotations.Api;
import lombok.Data;

/**
 * Created by caryc on 2017/4/10.
 */
@Data
public class AuthRequest extends ApiRequest {
    private static final long serialVersionUID = -11111100001L;

    private String username;
    private String password;
    private String captcha;
}
