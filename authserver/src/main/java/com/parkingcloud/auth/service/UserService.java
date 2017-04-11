package com.parkingcloud.auth.service;

import com.parkingcloud.auth.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caryc on 2017/3/28.
 */
public interface UserService {
    Boolean isUserExist(String phoneNumber);
    Boolean createUser(String phoneNumber,String password);
    User getUserByPhoneNumber(String phoneNumber);
    Boolean resetUserPassword(String phoneNumber,String password);
}
