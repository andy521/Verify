package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.AccountRegisterLog;
import com.orange.verify.api.service.AccountRegisterLogService;
import com.orange.verify.api.vo.AccountRegisterLogVo;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "accountRegisterLog")
public class AccountRegisterLogController {

    @Reference
    private AccountRegisterLogService accountRegisterLogService;

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "getBeforeData",method = RequestMethod.GET)
    @ResponseBody
    public Response getBeforeData(String softId) {

        List<String> beforeData = accountRegisterLogService.getBeforeData(softId);
        return Response.build(ResponseCode.QUERY_SUCCESS,beforeData);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Response page(AccountRegisterLog accountRegisterLog, Page page) {

        Page<AccountRegisterLogVo> accountLoginLogVoPage = accountRegisterLogService.page(accountRegisterLog,page);

        return Response.build(ResponseCode.QUERY_SUCCESS,accountLoginLogVoPage);
    }

}
