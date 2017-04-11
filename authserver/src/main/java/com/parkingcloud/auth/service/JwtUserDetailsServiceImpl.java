package com.parkingcloud.auth.service;

import com.parkingcloud.auth.domain.User;
import com.parkingcloud.auth.domain.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by caryc on 2017/4/6.
 */
@Service
@Primary
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // "PhoneNumber" as user's username
        User user = userService.getUserByPhoneNumber(username);
        if (user == null) {
            // If we cann't find the user name, we should throw UsernameNotFoundException here,
            // otherwise, system will throw NullPointerException,
            throw new UsernameNotFoundException(String.format("Username '%s' was not found", username));
        }
        return AuthUserDetails.createJwtUser(user,null);
    }
}
