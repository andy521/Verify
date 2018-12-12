package com.orange.verify.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orange.verify.api.bean.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_user")
    List<User> get();

}