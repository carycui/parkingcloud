package com.parkingcloud.auth.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caryc on 2017/4/6.
 * General app response class
 */
@Data
public class ApiResponse implements Serializable {
    private static final long serialVersionUID = -2L;
    // App response code define
    public static final int CodeSuccess= 0;
    public static final int CodeFailure = -1;
    // Response error code
    private int errorCode = 0;
    // Response error message
    private String  errorMessage;
    // Response service name
    private String  service;
    // Response extra data map
    private Map<String,Object> data = null;

    /**
     * Construction
     */
    protected ApiResponse(){
        super();
    }

    /**
     * Construction
     * @param errorCode Response error code
     * @param errorMessage Response error message
     */
    protected ApiResponse(int errorCode, String errorMessage){
        super();
        this.errorCode =errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Construction
     * @param e Exception
     */
    protected ApiResponse(Exception e){
        super();
        this.errorCode =CodeFailure;
        this.errorMessage = e.getMessage();
    }

    /**
     * Set extra data for response
     * @param key Data key
     * @param value Data value
     * @return AppResonse
     */
    public ApiResponse setData(String key, Object value){
        if (data==null){
            data = new HashMap<String,Object>();
        }
        data.put(key,value);
        return this;
    }

    /**
     * Get extra data by key
     * @param key The data key
     * @return The data object
     */
    public Object getData(String key){
        if (data==null){
            return null;
        }
        return data.get(key);
    }
}
