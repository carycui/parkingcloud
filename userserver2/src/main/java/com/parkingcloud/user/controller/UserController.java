package com.parkingcloud.user.controller;

import com.parkingcloud.user.UserServerApplication;
import com.parkingcloud.user.domain.User;
import com.parkingcloud.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by caryc on 2017/3/28.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    List<User> getUsers(){
        return userService.getUsers();
    }
    @GetMapping("/{id}")
    User getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

}
