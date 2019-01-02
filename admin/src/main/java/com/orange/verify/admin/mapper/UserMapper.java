package com.orange.verify.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orange.verify.api.bean.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    @Select("select count(*) from t_user where username = #{username} and password = #{password} and del_flag=0")
    int verifyUser(@Param("username") String username,@Param("password") String password);

}