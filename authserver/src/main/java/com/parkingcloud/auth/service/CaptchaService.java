package com.parkingcloud.auth.service;

import com.parkingcloud.auth.domain.AuthResponse;

/**
 * Created by caryc on 2017/4/6.
 */
public interface CaptchaService {
    String getCaptcha(String phoneNumber);
    String getCachedCaptcha(String phoneNumber);
}
