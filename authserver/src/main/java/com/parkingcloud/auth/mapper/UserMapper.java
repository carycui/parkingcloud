package com.parkingcloud.auth.mapper;

import com.parkingcloud.auth.domain.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by caryc on 2017/4/10.
 */

@Mapper
public interface UserMapper {

    @Insert(value="INSERT INTO t_user (uid,phoneNumber,password,passwordResetTime,enabled,locked,updateTime,createTime) \n" +
            "values(#{uid,jdbcType=VARCHAR},#{phoneNumber,jdbcType=VARCHAR},#{password,jdbcType=VARCHAR},NOW(),#{enabled,jdbcType=TINYINT},#{locked,jdbcType=TINYINT},NOW(),NOW())")
    Long createUser(User user);

    @Update(value="UPDATE t_user SET password=#{password,jdbcType=VARCHAR},passwordResetTime=NOW() " +
            "WHERE phoneNumber=#{phoneNumber,jdbcType=VARCHAR}")
    Long resetUserPassword(@Param("phoneNumber") String phoneNumber, @Param("password") String password);

    @Select(value="SELECT uid,phoneNumber,password,enabled,locked FROM t_user " +
            "WHERE phoneNumber=#{phoneNumber,jdbcType=VARCHAR}")
    User findUserByPhoneNumber(String phoneNumber);
}
