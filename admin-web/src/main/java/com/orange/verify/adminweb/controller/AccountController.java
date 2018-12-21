package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.ParameterError;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.Account;
import com.orange.verify.api.model.ServiceResult;
import com.orange.verify.api.service.AccountService;
import com.orange.verify.api.vo.AccountVo;
import com.orange.verify.api.vo.open.AccountLoginVo;
import com.orange.verify.api.vo.open.AccountRegisterVo;
import com.orange.verify.common.ip.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Api(description = "用户接口")
@Controller
@RequestMapping(value = "account")
public class AccountController extends BaseController {

    @Reference
    private AccountService accountService;

    @ApiOperation(value = "获取用户-需要验证api")
    @RspHandle
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Response page(AccountVo accountVo, Page page) {

        Page<AccountVo> cardTypeVoPage = accountService.page(accountVo,page);
        return Response.build(ResponseCode.QUERY_SUCCESS,cardTypeVoPage);
    }

    @ApiOperation(value = "获取用户数量-需要验证api")
    @RspHandle
    @RequestMapping(value = "count",method = RequestMethod.GET)
    @ResponseBody
    public Response count() {

        int count = accountService.count();
        return Response.build(ResponseCode.QUERY_SUCCESS,count);
    }

    @ApiOperation(value = "用户黑名单设置-需要验证api")
    @RspHandle
    @RequestMapping(value = "blacklist",method = RequestMethod.POST)
    @ResponseBody
    public Response blacklist(String accountId,Integer blacklist) {

        Account account = new Account();
        account.setId(accountId);
        account.setBlacklist(blacklist);
        boolean b = accountService.updateById(account);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @ApiOperation(value = "删除用户-需要验证api")
    @RspHandle
    @RequestMapping(value = "remove",method = RequestMethod.POST)
    @ResponseBody
    public Response remove(String accountId) {

        boolean b = accountService.removeById(accountId);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @ApiOperation(value = "获取rsa钥匙-开放接口")
    @RspHandle(ipHandle = true)
    @RequestMapping(value = "getPublicKey",method = RequestMethod.POST)
    @ResponseBody
    public Response getPublicKey() {

        ServiceResult<String> result = accountService.getPublicKey();
        switch (result.getCode()) {
            case 1:
                return Response.build(ResponseCode.QUERY_SUCCESS,ResponseCode.QUERY_SUCCESS.getDesc(),result.getData());
            case 3:
                return Response.build(ResponseCode.KEY_ERROR);
            default:
                return Response.build(ResponseCode.ERROR);
        }

    }

    @ApiOperation(value = "用户注册-开放接口")
    @RspHandle(ipHandle = true)
    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public Response register(@Validated AccountRegisterVo accountRegisterVo, BindingResult result,
                             HttpServletRequest request) throws ParameterError {

        parametric(result);

        accountRegisterVo.setPublicKey(accountRegisterVo.getPublicKey().replaceAll(" ","+"));
        accountRegisterVo.setPassword(accountRegisterVo.getPassword().replaceAll(" ","+"));
        accountRegisterVo.setCode(accountRegisterVo.getCode().replaceAll(" ","+"));

        accountRegisterVo.setIp(IpUtil.getIp(request));

        ServiceResult<Integer> register = accountService.register(accountRegisterVo);
        switch (register.getCode()) {
            case 1:
                if (register.getData() == 1) {
                    return Response.build(ResponseCode.REGISTER_SUCCESS);
                }
                return Response.build(ResponseCode.REGISTER_ERROR);
            case 2:
                return Response.build(ResponseCode.KEY_EMPTY);
            case 3:
                return Response.build(ResponseCode.SOFT_EMPTY);
            case 4:
                return Response.build(ResponseCode.BAIDU_API_ERROR);
            case 5:
                return Response.build(ResponseCode.KEY_ERROR);
            case 6:
                return Response.build(ResponseCode.ACCOUNT_ALREADY_EXIST);
            case 7:
                return Response.build(ResponseCode.PASSWORD_LENGTH_ERROR);
            case 8:
                return Response.build(ResponseCode.SOFT_CLOSE,register.getMsg());
            case 9:
                return Response.build(ResponseCode.REGISTER_CLOSE,register.getMsg());
            default:
                return Response.build(ResponseCode.ERROR);
        }

    }

    @ApiOperation(value = "用户登陆-开放接口")
    @RspHandle
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Response login(@Validated AccountLoginVo accountLoginVo, BindingResult result,
                          HttpServletRequest request) throws ParameterError {

        parametric(result);

        accountLoginVo.setPublicKey(accountLoginVo.getPublicKey().replaceAll(" ","+"));
        accountLoginVo.setPassword(accountLoginVo.getPassword().replaceAll(" ","+"));
        accountLoginVo.setCode(accountLoginVo.getCode().replaceAll(" ","+"));

        accountLoginVo.setIp(IpUtil.getIp(request));

        ServiceResult<Integer> login = accountService.login(accountLoginVo);
        switch (login.getCode()) {
            case 1:
                return Response.build(ResponseCode.LOGIN_SUCCESS);
            case 2:
                return Response.build(ResponseCode.LOGIN_ERROR);
            case 3:
                return Response.build(ResponseCode.KEY_EMPTY);
            case 4:
                return Response.build(ResponseCode.SOFT_EMPTY);
            case 5:
                return Response.build(ResponseCode.KEY_ERROR);
            case 6:
                return Response.build(ResponseCode.PASSWORD_LENGTH_ERROR);
            default:
                return Response.build(ResponseCode.ERROR);
        }
    }

}
