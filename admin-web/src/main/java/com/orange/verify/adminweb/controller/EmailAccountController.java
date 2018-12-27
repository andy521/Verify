package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.EmailAccount;
import com.orange.verify.api.service.EmailAccountService;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "emailAccount")
public class EmailAccountController {

    @Reference
    private EmailAccountService emailAccountService;

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public Response list() {

        List<EmailAccount> list = emailAccountService.getList();
        return Response.build(ResponseCode.QUERY_SUCCESS,list);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "single",method = RequestMethod.GET)
    @ResponseBody
    public Response single(String emailAccountId) {

        EmailAccount emailAccount = emailAccountService.getById(emailAccountId);
        return Response.build(ResponseCode.QUERY_SUCCESS,emailAccount);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Response create(EmailAccount emailAccount) {

        boolean b = emailAccountService.save(emailAccount);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Response update(EmailAccount emailAccount) {

        boolean b = emailAccountService.updateById(emailAccount);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @RspHandle
    @RequiresUser
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



