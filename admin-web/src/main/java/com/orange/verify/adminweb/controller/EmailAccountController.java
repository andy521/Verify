package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.EmailAccount;
import com.orange.verify.api.service.EmailAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(description = "邮箱账户录入")
@Controller
@RequestMapping(value = "emailAccount")
public class EmailAccountController {

    @Reference
    private EmailAccountService emailAccountService;

    @ApiOperation(value = "获取邮箱账户-需要验证api")
    @RspHandle
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public Response list() {

        List<EmailAccount> list = emailAccountService.getList();
        return Response.build(ResponseCode.QUERY_SUCCESS,list);
    }

    @ApiOperation(value = "获取单个邮箱账户-需要验证api")
    @RspHandle
    @RequestMapping(value = "single",method = RequestMethod.GET)
    @ResponseBody
    public Response single(String emailAccountId) {

        EmailAccount emailAccount = emailAccountService.getById(emailAccountId);
        return Response.build(ResponseCode.QUERY_SUCCESS,emailAccount);
    }

    @ApiOperation(value = "增加邮箱账户-需要验证api")
    @RspHandle
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Response create(EmailAccount emailAccount) {

        boolean b = emailAccountService.save(emailAccount);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @ApiOperation(value = "修改邮箱账户-需要验证api")
    @RspHandle
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Response update(EmailAccount emailAccount) {

        boolean b = emailAccountService.updateById(emailAccount);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @ApiOperation(value = "删除邮箱账户-需要验证api")
    @RspHandle
    @RequestMapping(value = "remove",method = RequestMethod.POST)
    @ResponseBody
    public Response remove(String emailAccountId) {

        boolean b = emailAccountService.removeById(emailAccountId);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

}



