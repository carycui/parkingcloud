package com.parkingcloud.auth.service;

import com.parkingcloud.auth.exception.SmsProviderException;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by caryc on 2017/4/6.
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    private static final Random random = new Random();
    private static final int CAPTCHA_LENGTH = 4;
    // TODO: We should use global redis to catch the captcha
    private ConcurrentHashMap<String,String> catchedCaptcha = new ConcurrentHashMap<String,String>();

    /**
     * Generate captcha string, and length is 4
     * @return
     */
    private String generateCaptcha(){
        int value = random.nextInt(10^CAPTCHA_LENGTH);
        return String.valueOf(value);
    }

    private void sendSms(String phoneNumber,String captcha) throws SmsProviderException {
        // TODO: We should call service provider to send real captcha via SMS using template
        // throw new SmsProviderException("No sms provider");
    }

    @Override
    public String getCaptcha(String phoneNumber) {
        String captcha = generateCaptcha();
        sendSms(phoneNumber,captcha);
        catchedCaptcha.put(phoneNumber,captcha);
        return captcha;
    }

    @Override
    public String getCachedCaptcha(String phoneNumber) {
        return catchedCaptcha.get(phoneNumber);
    }


}
