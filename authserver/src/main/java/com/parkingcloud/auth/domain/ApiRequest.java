package com.parkingcloud.auth.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by caryc on 2017/4/10.
 */
@Data
public class ApiRequest implements Serializable {
    private static final long serialVersionUID = -11111100000L;
    private String client;
    private String version;
    private String device;

}
