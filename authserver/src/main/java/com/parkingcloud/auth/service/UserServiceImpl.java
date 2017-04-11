package com.parkingcloud.auth.service;

import com.parkingcloud.auth.domain.User;
import com.parkingcloud.auth.mapper.UserMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by caryc on 2017/3/28.
 */
@Service
public class UserServiceImpl implements UserService{
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    UserMapper userMapper;

    @Override
    public Boolean isUserExist(String phoneNumber){
        try {
            User u = userMapper.findUserByPhoneNumber(phoneNumber);
            return u!=null;
        }catch (Exception e){
            logger.error("Find user by phone number excetion",e);
        }
        return false;
    }

    @Override
    public Boolean createUser(String phoneNumber, String password) {
        try {
            User u = new User();
            // UUID can use the phone number's hash value since it's global unique
            u.setUid(UUID.randomUUID().toString());
            u.setPhoneNumber(phoneNumber);
            u.setPassword(passwordEncoder.encode(password));
            u.setEnabled(1);
            u.setLocked(0);
            Long ret = userMapper.createUser(u);
            return ret==1L;
        } catch (Exception e){
            logger.error("Create user exception",e);
        }
        return false;
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber){
        return userMapper.findUserByPhoneNumber(phoneNumber);
    }

    @Override
    public Boolean resetUserPassword(String phoneNumber, String password) {
        try  {
            Long ret = userMapper.resetUserPassword(phoneNumber,passwordEncoder.encode(password));
            return ret==1L;
        } catch (Exception e){
            logger.error("Reset user password exception",e);
        }
        return false;
    }
}
