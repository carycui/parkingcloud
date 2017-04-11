package com.parkingcloud.auth;

import com.parkingcloud.auth.domain.AuthResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserServerApplicationTests {

    private static final String TEST_PHONENUMBER = "13016511751";
    private static final String TEST_PASSWORD = "mypassword";
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Value("${jwt.header}")
    private String tokenHeader;

    private String userToken;

    @Test
    public void testReisterUser() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        JSONObject parm = new JSONObject();
        parm.put("username", TEST_PHONENUMBER);
        HttpEntity httpEntity = new HttpEntity(parm.toString(), headers);
        // Get captcha
        ResponseEntity<?> resp = testRestTemplate.postForEntity("/auth/captcha", httpEntity, AuthResponse.class);
        Assert.assertTrue(resp.getStatusCode() == HttpStatus.OK);
        // Register user with captcha and password
        AuthResponse authResponse = (AuthResponse) resp.getBody();
        String captcha = (String) authResponse.getData().get("captcha");
        parm.put("captcha", captcha);
        parm.put("password", TEST_PASSWORD);
        httpEntity = new HttpEntity(parm.toString(), headers);
        resp = testRestTemplate.postForEntity("/auth/register", httpEntity, AuthResponse.class);
        Assert.assertTrue(resp.getStatusCode() == HttpStatus.OK);

    }

    @Test
    public void testLoginUser() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        JSONObject parm = new JSONObject();
        parm.put("username", TEST_PHONENUMBER);
        parm.put("password", TEST_PASSWORD);
        HttpEntity httpEntity = new HttpEntity(parm.toString(), headers);
        ResponseEntity<?> resp = testRestTemplate.postForEntity("/auth/login", httpEntity, AuthResponse.class);
        Assert.assertTrue(resp.getStatusCode() == HttpStatus.OK);
        AuthResponse authResponse = (AuthResponse) resp.getBody();
        userToken = (String) authResponse.getData("token");
        Assert.assertFalse(StringUtils.isEmpty(userToken));
    }

    @Test
    public void testResetPassword() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        JSONObject parm = new JSONObject();
        headers.add(tokenHeader, userToken);
        parm.put("password", TEST_PASSWORD);
        HttpEntity httpEntity = new HttpEntity(parm.toString(), headers);
        ResponseEntity<?> resp = testRestTemplate.postForEntity("/auth/reset", httpEntity, AuthResponse.class);
        Assert.assertTrue(resp.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void testRefreshTolen() throws  Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        JSONObject parm = new JSONObject();
        headers.add(tokenHeader, userToken);
        HttpEntity httpEntity = new HttpEntity(parm.toString(), headers);
        ResponseEntity<?> resp = testRestTemplate.postForEntity("/auth/refresh", httpEntity, AuthResponse.class);
        Assert.assertTrue(resp.getStatusCode() == HttpStatus.OK);
        userToken = (String) ((AuthResponse) resp.getBody()).getData("token");
        Assert.assertFalse(StringUtils.isEmpty(userToken));
    }
}
