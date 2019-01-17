package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.ParameterError;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.sr.ServiceResult;
import com.orange.verify.api.service.SoftLeaveMessageService;
import com.orange.verify.api.sr.SoftLeaveMessageImplCreateEnum;
import com.orange.verify.api.vo.SoftLeaveMessageVo;
import com.orange.verify.api.vo.open.SoftLeaveMeesageSubmitVo;
import com.orange.verify.common.ip.IpUtil;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "softLeaveMessage")
public class SoftLeaveMessageController extends BaseController {

    @Reference
    private SoftLeaveMessageService softLeaveMessageService;

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Response page(SoftLeaveMessageVo softLeaveMessageVo, Page page) {

        Page<SoftLeaveMessageVo> softLeaveMessagePage = softLeaveMessageService.page(softLeaveMessageVo, page);

        return Response.build(ResponseCode.QUERY_SUCCESS,softLeaveMessagePage);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "remove",method = RequestMethod.POST)
    @ResponseBody
    public Response remove(String softLeaveMessageId) {

        boolean b = softLeaveMessageService.removeById(softLeaveMessageId);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @RspHandle
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Response create(@Validated SoftLeaveMeesageSubmitVo softLeaveMeesageSubmitVo, BindingResult result,
                           HttpServletRequest request) throws ParameterError {

        super.parametric(result);

        softLeaveMeesageSubmitVo.setIp(IpUtil.getIp(request));

        ServiceResult serviceResult = softLeaveMessageService.create(softLeaveMeesageSubmitVo);
        switch (serviceResult.getCode()) {
            case SoftLeaveMessageImplCreateEnum.LEAVE_MESSAGE_SEND_SUCCESS:
                return Response.build(ResponseCode.LEAVE_MESSAGE_SEND_SUCCESS);

            case SoftLeaveMessageImplCreateEnum.SOFT_EMPTY:
                return Response.build(ResponseCode.SOFT_EMPTY);

            case SoftLeaveMessageImplCreateEnum.SOFT_CLOSE:
                return Response.build(ResponseCode.SOFT_CLOSE);

            case SoftLeaveMessageImplCreateEnum.BAIDU_API_ERROR:
                return Response.build(ResponseCode.BAIDU_API_ERROR);

            case SoftLeaveMessageImplCreateEnum.LEAVE_MESSAGE_SEND_ERROR:
                return Response.build(ResponseCode.LEAVE_MESSAGE_SEND_ERROR);

            default:
                return Response.build(ResponseCode.UNKNOWN_ERROR);
        }
    }

}
