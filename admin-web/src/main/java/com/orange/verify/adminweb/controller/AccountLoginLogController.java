package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.service.AccountLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(description = "登陆日志")
@Controller
@RequestMapping(value = "accountLoginLog")
public class AccountLoginLogController extends BaseController {

    @Reference
    private AccountLoginLogService accountLoginLogService;

    @ApiOperation(value = "找出以往7天到现在的数据-需要验证api")
    @RspHandle
    @RequestMapping(value = "getBeforeData",method = RequestMethod.GET)
    @ResponseBody
    public Response getBeforeData() {

        List<String> beforeData = accountLoginLogService.getBeforeData();
        return Response.build(ResponseCode.QUERY_SUCCESS,beforeData);
    }

}
