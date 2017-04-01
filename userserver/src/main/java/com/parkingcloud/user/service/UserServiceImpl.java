package com.parkingcloud.user.service;

import com.parkingcloud.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caryc on 2017/3/28.
 */
@Service
public class UserServiceImpl implements UserService{
    @Override
    public User getUser(Long id){
        User u = new User();
        u.setId(id);
        u.setNickName("Nickname"+id);
        return u;
    }

    @Override
    public List<User> getUsers() {
        ArrayList al = new ArrayList<User>();
        for (Long i=1L;i<10L;i++){
            User u = new User();
            u.setId(i);
            u.setNickName("Nickname"+i);
            al.add(u);
        }
        return al;
    }


}
