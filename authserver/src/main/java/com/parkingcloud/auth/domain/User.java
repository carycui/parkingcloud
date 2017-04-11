package com.parkingcloud.auth.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by caryc on 2017/3/28.
 */
@Data
public class User {
    private String uid;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String email;
    private Integer sex;
    private Date birthday;
    private String address;
    private String imageUrl;
    private String password;
    private Date passwordResetTime;
    private Integer enabled = 1;
    private Integer locked = 0;
    private Date updateTime;
    private Date createTime;
}
