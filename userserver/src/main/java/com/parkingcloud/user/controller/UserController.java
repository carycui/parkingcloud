package com.parkingcloud.user.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.parkingcloud.user.UserServerApplication;
import com.parkingcloud.user.domain.User;
import com.parkingcloud.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caryc on 2017/3/28.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @HystrixCommand(fallbackMethod = "getUsersFallback")
    @GetMapping("/")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    public List<User> getUsersFallback(){
        return new ArrayList<User>();
    }

    @HystrixCommand(fallbackMethod = "getUserFallback")
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    public User getUserFallback(Long id){
        User u = new User();
        u.setId(-1L);
        u.setNickName("fallback user");
        return u;
    }

    @HystrixCommand(fallbackMethod = "testFallback")
    @GetMapping("/test/{v}")
    public String test(@PathVariable("v") Long v){
        try{
            Thread.sleep(v);
        }catch(Exception e){

        }
        return "normal test response";
    }

    public String testFallback(Long v){
        return "fallback test response";
    }

}
