package com.orange.verify.adminweb.config;

import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局拦截 异常
 * @author Orange
 * @date 2018/11/22
 */
@ControllerAdvice
public class ShiroFilterConfig {

    /**
     * 登录认证异常
     */
    @ExceptionHandler({ UnauthenticatedException.class, AuthenticationException.class })
    @ResponseBody
    public Response authenticationException(HttpServletRequest request, HttpServletResponse response) {
        return Response.build(ResponseCode.NOT_LOGIN);
    }

    /**
     * 权限异常
     */
    @ExceptionHandler({ UnauthorizedException.class, AuthorizationException.class })
    @ResponseBody
    public Response authorizationException(HttpServletRequest request, HttpServletResponse response) {
        return Response.build(ResponseCode.NOT_ROLE);
    }

}
