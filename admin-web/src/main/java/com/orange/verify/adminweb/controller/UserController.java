package com.orange.verify.adminweb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.User;
import com.orange.verify.api.vo.AccountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(description = "管理员登陆")
@Controller
@RequestMapping(value = "user")
public class UserController {

    @ApiOperation(value = "管理员登陆-需要验证api")
    @RspHandle
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Response login(@RequestBody User user) {

        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken("1", "1");
        // 执行认证登陆
        subject.login(token);

        return Response.build(ResponseCode.LOGIN_SUCCESS);
    }

}
