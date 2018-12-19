package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.SoftLeaveMessage;
import com.orange.verify.api.service.SoftLeaveMessageService;
import com.orange.verify.api.vo.SoftLeaveMessageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(description = "软件留言")
@Controller
@RequestMapping(value = "softLeaveMessage")
public class SoftLeaveMessageController {

    @Reference
    private SoftLeaveMessageService softLeaveMessageService;

    @ApiOperation(value = "获取反馈列表-需要验证api")
    @RspHandle
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Response page(SoftLeaveMessageVo softLeaveMessageVo, Page page) {

        Page<SoftLeaveMessage> softLeaveMessagePage = softLeaveMessageService.page(softLeaveMessageVo, page);

        return Response.build(ResponseCode.QUERY_SUCCESS,softLeaveMessagePage);
    }

    @ApiOperation(value = "删除留言-需要验证api")
    @RspHandle
    @RequestMapping(value = "remove",method = RequestMethod.POST)
    @ResponseBody
    public Response remove(String softLeaveMessageId) {

        boolean b = softLeaveMessageService.removeById(softLeaveMessageId);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

}
