package com.parkingcloud.user.domain;

import lombok.Data;

/**
 * Created by caryc on 2017/3/28.
 */
@Data
public class User {
    private Long id;
    private String phoneNumber;
    private String nickName;
    private Integer sex;
    private String birthday;
    private String address;
    private String imageUrl;
    private String password;
    private String passwordSalt;
}
