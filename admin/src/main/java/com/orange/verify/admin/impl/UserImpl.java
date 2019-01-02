package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.UserMapper;
import com.orange.verify.api.bean.User;
import com.orange.verify.api.service.UserService;

@Service
public class UserImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public int verifyUser(String username, String password) {

        return super.baseMapper.verifyUser(username,password);
    }

}
