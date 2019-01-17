package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.service.AccountLoginLogService;
import com.orange.verify.api.vo.AccountLoginLogVo;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "accountLoginLog")
public class AccountLoginLogController extends BaseController {

    @Reference
    private AccountLoginLogService accountLoginLogService;

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "getBeforeData",method = RequestMethod.GET)
    @ResponseBody
    public Response getBeforeData(String softId) {

        List<String> beforeData = accountLoginLogService.getBeforeData(softId);
        return Response.build(ResponseCode.QUERY_SUCCESS,beforeData);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Response page(AccountLoginLogVo accountLoginLog, Page page) {

        Page<AccountLoginLogVo> accountLoginLogVoPage = accountLoginLogService.page(accountLoginLog, page);

        return Response.build(ResponseCode.QUERY_SUCCESS,accountLoginLogVoPage);
    }

}
