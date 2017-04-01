package com.parkingcloud.user.service;

import com.parkingcloud.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caryc on 2017/3/28.
 */
public interface UserService {
    User getUser(Long id);
    List<User> getUsers();
}
