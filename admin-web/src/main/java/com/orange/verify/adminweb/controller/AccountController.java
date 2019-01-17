package com.orange.verify.adminweb.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.ParameterError;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.Account;
import com.orange.verify.api.sr.ServiceResult;
import com.orange.verify.api.service.AccountService;
import com.orange.verify.api.sr.*;
import com.orange.verify.api.vo.AccountVo;
import com.orange.verify.api.vo.open.*;
import com.orange.verify.common.ip.IpUtil;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "account")
public class AccountController extends BaseController {

    @Reference
    private AccountService accountService;

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Response page(AccountVo accountVo, Page page) {

        Page<AccountVo> cardTypeVoPage = accountService.page(accountVo,page);
        return Response.build(ResponseCode.QUERY_SUCCESS,cardTypeVoPage);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "count",method = RequestMethod.GET)
    @ResponseBody
    public Response count() {

        int count = accountService.count();
        return Response.build(ResponseCode.QUERY_SUCCESS,count);
    }

    @RspHandle
    @RequiresUser
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

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "remove",method = RequestMethod.POST)
    @ResponseBody
    public Response remove(String accountId) {

        boolean b = accountService.removeById(accountId);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @RspHandle
    @RequestMapping(value = "getPublicKey",method = RequestMethod.POST)
    @ResponseBody
    public Response getPublicKey() {

        ServiceResult<String> result = accountService.getPublicKey();
        switch (result.getCode()) {
            case AccountImplGetPublicKeyEnum.SUCCESS:
                return Response.build(ResponseCode.QUERY_SUCCESS,ResponseCode.QUERY_SUCCESS.getDesc(),result.getData());

            case AccountImplGetPublicKeyEnum.KEY_ERROR:
                return Response.build(ResponseCode.KEY_ERROR);

            default:
                return Response.build(ResponseCode.UNKNOWN_ERROR);
        }

    }

    @RequestMapping(value = "getVerificationCode",method = RequestMethod.GET)
    public void getVerificationCode(AccountVerificationCodeVo accountVerificationCodeVo,HttpServletResponse response) {

        ICaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 6, 20);

        try {
            if (accountVerificationCodeVo == null) {
                accountVerificationCodeVo = new AccountVerificationCodeVo();
            }
            accountVerificationCodeVo.setPublicKey(accountVerificationCodeVo.getPublicKey()
                    .replaceAll(" ","+"));
            accountVerificationCodeVo.setCode(captcha.getCode());
            accountService.saveVerificationCode(accountVerificationCodeVo);
            captcha.write(response.getOutputStream());
        } catch (IOException e) {
        }
    }

    @RspHandle
    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public Response register(@Validated AccountRegisterVo accountRegisterVo, BindingResult result,
                             HttpServletRequest request) throws ParameterError {

        super.parametric(result);

        accountRegisterVo.setPublicKey(accountRegisterVo.getPublicKey().replaceAll(" ","+"));
        accountRegisterVo.setPassword(accountRegisterVo.getPassword().replaceAll(" ","+"));
        accountRegisterVo.setCode(accountRegisterVo.getCode().replaceAll(" ","+"));

        accountRegisterVo.setIp(IpUtil.getIp(request));

        ServiceResult register = accountService.register(accountRegisterVo);
        switch (register.getCode()) {
            case AccountImplRegisterEnum.REGISTER_SUCCESS:
                return Response.build(ResponseCode.REGISTER_SUCCESS);

            case AccountImplRegisterEnum.REGISTER_ERROR:
                return Response.build(ResponseCode.REGISTER_ERROR);

            case AccountImplRegisterEnum.KEY_EMPTY:
                return Response.build(ResponseCode.KEY_EMPTY);

            case AccountImplRegisterEnum.SOFT_EMPTY:
                return Response.build(ResponseCode.SOFT_EMPTY);

            case AccountImplRegisterEnum.BAIDU_API_ERROR:
                return Response.build(ResponseCode.BAIDU_API_ERROR);

            case AccountImplRegisterEnum.KEY_ERROR:
                return Response.build(ResponseCode.KEY_ERROR);

            case AccountImplRegisterEnum.ACCOUNT_ALREADY_EXIST:
                return Response.build(ResponseCode.ACCOUNT_ALREADY_EXIST);

            case AccountImplRegisterEnum.PASSWORD_LENGTH_ERROR:
                return Response.build(ResponseCode.PASSWORD_LENGTH_ERROR);

            case AccountImplRegisterEnum.SOFT_CLOSE:
                return Response.build(ResponseCode.SOFT_CLOSE,register.getMsg());

            case AccountImplRegisterEnum.REGISTER_CLOSE:
                return Response.build(ResponseCode.REGISTER_CLOSE,register.getMsg());

            case AccountImplRegisterEnum.VC_EMPTY:
                return Response.build(ResponseCode.VC_EMPTY);

            case AccountImplRegisterEnum.VC_MISMATCHES:
                return Response.build(ResponseCode.VC_MISMATCHES);

            case AccountImplRegisterEnum.NIMIETY:
                return Response.build(ResponseCode.NIMIETY);

            case AccountImplRegisterEnum.SERVER_ERROR:
                return Response.build(ResponseCode.SERVER_ERROR);

            default:
                return Response.build(ResponseCode.UNKNOWN_ERROR);
        }

    }

    @RspHandle
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Response login(@Validated AccountLoginVo accountLoginVo, BindingResult result,
                          HttpServletRequest request) throws ParameterError {

        super.parametric(result);

        accountLoginVo.setPublicKey(accountLoginVo.getPublicKey().replaceAll(" ","+"));
        accountLoginVo.setPassword(accountLoginVo.getPassword().replaceAll(" ","+"));
        accountLoginVo.setCode(accountLoginVo.getCode().replaceAll(" ","+"));

        accountLoginVo.setIp(IpUtil.getIp(request));

        ServiceResult login = accountService.login(accountLoginVo);
        switch (login.getCode()) {
            case AccountImplLoginEnum.LOGIN_SUCCESS:
                return Response.build(ResponseCode.LOGIN_SUCCESS);

            case AccountImplLoginEnum.LOGIN_ERROR:
                return Response.build(ResponseCode.LOGIN_ERROR);

            case AccountImplLoginEnum.KEY_EMPTY:
                return Response.build(ResponseCode.KEY_EMPTY);

            case AccountImplLoginEnum.SOFT_EMPTY:
                return Response.build(ResponseCode.SOFT_EMPTY);

            case AccountImplLoginEnum.KEY_ERROR:
                return Response.build(ResponseCode.KEY_ERROR);

            case AccountImplLoginEnum.PASSWORD_LENGTH_ERROR:
                return Response.build(ResponseCode.PASSWORD_LENGTH_ERROR);

            case AccountImplLoginEnum.SOFT_CLOSE:
                return Response.build(ResponseCode.SOFT_CLOSE,login.getMsg());

            case AccountImplLoginEnum.ACCOUNT_NOT_BOUND_CARD:
                return Response.build(ResponseCode.ACCOUNT_NOT_BOUND_CARD);

            case AccountImplLoginEnum.CARD_CLOSURE:
                return Response.build(ResponseCode.CARD_CLOSURE);

            case AccountImplLoginEnum.CARD_PAST_DUE:
                return Response.build(ResponseCode.CARD_PAST_DUE);

            case AccountImplLoginEnum.ACCOUNT_BLACKLIST:
                return Response.build(ResponseCode.ACCOUNT_BLACKLIST);

            case AccountImplLoginEnum.BAIDU_API_ERROR:
                return Response.build(ResponseCode.BAIDU_API_ERROR);

            case AccountImplLoginEnum.ACCOUNT_EMPTY:
                return Response.build(ResponseCode.ACCOUNT_EMPTY);

            case AccountImplLoginEnum.PASSWORD_ERROR:
                return Response.build(ResponseCode.PASSWORD_ERROR);

            default:
                return Response.build(ResponseCode.UNKNOWN_ERROR);
        }
    }

    @RspHandle
    @RequestMapping(value = "bindingCard",method = RequestMethod.POST)
    @ResponseBody
    public Response bindingCard(@Validated AccountBindingCardVo accountBindingCardVo, BindingResult result)
            throws ParameterError {

        super.parametric(result);

        accountBindingCardVo.setPublicKey(accountBindingCardVo.getPublicKey().replaceAll(" ","+"));
        accountBindingCardVo.setPassword(accountBindingCardVo.getPassword().replaceAll(" ","+"));
        accountBindingCardVo.setCode(accountBindingCardVo.getCode().replaceAll(" ","+"));

        ServiceResult bindingCard = accountService.bindingCard(accountBindingCardVo);

        switch (bindingCard.getCode()) {
            case AccountImplBindingCardEnum.BINDING_CARD_SUCCESS:
                return Response.build(ResponseCode.BINDING_CARD_SUCCESS);

            case AccountImplBindingCardEnum.BINDING_CARD_ERROR:
                return Response.build(ResponseCode.BINDING_CARD_ERROR);

            case AccountImplBindingCardEnum.KEY_EMPTY:
                return Response.build(ResponseCode.KEY_EMPTY);

            case AccountImplBindingCardEnum.SOFT_EMPTY:
                return Response.build(ResponseCode.SOFT_EMPTY);

            case AccountImplBindingCardEnum.KEY_ERROR:
                return Response.build(ResponseCode.KEY_ERROR);

            case AccountImplBindingCardEnum.PASSWORD_LENGTH_ERROR:
                return Response.build(ResponseCode.PASSWORD_LENGTH_ERROR);

            case AccountImplBindingCardEnum.SOFT_CLOSE:
                return Response.build(ResponseCode.SOFT_CLOSE,bindingCard.getMsg());

            case AccountImplBindingCardEnum.ACCOUNT_EMPTY:
                return Response.build(ResponseCode.ACCOUNT_EMPTY);

            case AccountImplBindingCardEnum.PASSWORD_ERROR:
                return Response.build(ResponseCode.PASSWORD_ERROR);

            case AccountImplBindingCardEnum.CARD_EMPTY:
                return Response.build(ResponseCode.CARD_EMPTY);

            case AccountImplBindingCardEnum.ACCOUNT_BLACKLIST:
                return Response.build(ResponseCode.ACCOUNT_BLACKLIST);

            case AccountImplBindingCardEnum.CARD_USE:
                return Response.build(ResponseCode.CARD_USE);

            case AccountImplBindingCardEnum.CARD_CLOSURE:
                return Response.build(ResponseCode.CARD_CLOSURE);

            case AccountImplBindingCardEnum.SOFT_INCONSISTENCY:
                return Response.build(ResponseCode.SOFT_INCONSISTENCY);

            case AccountImplBindingCardEnum.SOFT_FREE:
                return Response.build(ResponseCode.SOFT_FREE);

            default:
                return Response.build(ResponseCode.UNKNOWN_ERROR);
        }
    }

    @RspHandle
    @RequestMapping(value = "bindingCode",method = RequestMethod.POST)
    @ResponseBody
    public Response bindingCode(@Validated AccountBindingCodeVo accountBindingCodeVo, BindingResult result)
            throws ParameterError {

        super.parametric(result);

        accountBindingCodeVo.setPublicKey(accountBindingCodeVo.getPublicKey().replaceAll(" ","+"));
        accountBindingCodeVo.setPassword(accountBindingCodeVo.getPassword().replaceAll(" ","+"));
        accountBindingCodeVo.setCode(accountBindingCodeVo.getCode().replaceAll(" ","+"));

        ServiceResult bindingCode = accountService.bindingCode(accountBindingCodeVo);
        switch (bindingCode.getCode()) {
            case AccountImplBindingCodeEnum.BINDING_CODE_SUCCESS:
                return Response.build(ResponseCode.BINDING_CODE_SUCCESS);

            case AccountImplBindingCodeEnum.BINDING_CODE_ERROR:
                return Response.build(ResponseCode.BINDING_CODE_ERROR);

            case AccountImplBindingCodeEnum.KEY_EMPTY:
                return Response.build(ResponseCode.KEY_EMPTY);

            case AccountImplBindingCodeEnum.SOFT_EMPTY:
                return Response.build(ResponseCode.SOFT_EMPTY);

            case AccountImplBindingCodeEnum.KEY_ERROR:
                return Response.build(ResponseCode.KEY_ERROR);

            case AccountImplBindingCodeEnum.PASSWORD_LENGTH_ERROR:
                return Response.build(ResponseCode.PASSWORD_LENGTH_ERROR);

            case AccountImplBindingCodeEnum.SOFT_CLOSE:
                return Response.build(ResponseCode.SOFT_CLOSE,bindingCode.getMsg());

            case AccountImplBindingCodeEnum.ACCOUNT_EMPTY:
                return Response.build(ResponseCode.ACCOUNT_EMPTY);

            case AccountImplBindingCodeEnum.SOFT_NO_CHANGE:
                return Response.build(ResponseCode.SOFT_NO_CHANGE);

            case AccountImplBindingCodeEnum.ACCOUNT_BLACKLIST:
                return Response.build(ResponseCode.ACCOUNT_BLACKLIST);

            case AccountImplBindingCodeEnum.PASSWORD_ERROR:
                return Response.build(ResponseCode.PASSWORD_ERROR);

            default:
                return Response.build(ResponseCode.UNKNOWN_ERROR);
        }
    }

    @RspHandle
    @RequestMapping(value = "updatePassword",method = RequestMethod.POST)
    @ResponseBody
    public Response updatePassword(@Validated AccountUpdatePasswordVo accountUpdatePasswordVo, BindingResult result)
            throws ParameterError {

        super.parametric(result);

        ServiceResult bindingCode = accountService.updatePassword(accountUpdatePasswordVo);
        switch (bindingCode.getCode()) {
            case AccountImplUpdatePasswordEnum.UPDATE_PASSWORD_SUCCESS:
                return Response.build(ResponseCode.UPDATE_PASSWORD_SUCCESS);

            case AccountImplUpdatePasswordEnum.UPDATE_PASSWORD_ERROR:
                return Response.build(ResponseCode.UPDATE_PASSWORD_ERROR);

            case AccountImplUpdatePasswordEnum.SOFT_EMPTY:
                return Response.build(ResponseCode.SOFT_EMPTY);

            case AccountImplUpdatePasswordEnum.SOFT_CLOSE:
                return Response.build(ResponseCode.SOFT_CLOSE);

            case AccountImplUpdatePasswordEnum.ACCOUNT_EMPTY:
                return Response.build(ResponseCode.ACCOUNT_EMPTY);

            case AccountImplUpdatePasswordEnum.ACCOUNT_BLACKLIST:
                return Response.build(ResponseCode.ACCOUNT_BLACKLIST);

            case AccountImplUpdatePasswordEnum.SECURITY_CODE_ERROR:
                return Response.build(ResponseCode.SECURITY_CODE_ERROR);

            default:
                return Response.build(ResponseCode.UNKNOWN_ERROR);
        }
    }

}
