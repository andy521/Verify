package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.User;

public interface UserService extends IService<User> {

    int verifyUser(String username,String password);

}
