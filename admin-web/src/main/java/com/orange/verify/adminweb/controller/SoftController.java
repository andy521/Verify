package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.Soft;
import com.orange.verify.api.service.SoftService;
import com.orange.verify.api.vo.SoftVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(description = "软件")
@Controller
@RequestMapping(value = "soft")
public class SoftController {

    @Reference
    private SoftService softService;

    @ApiOperation(value = "获取分页软件-需要验证api")
    @RspHandle
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Response page(Soft soft, Page page) {

        Page<SoftVo> softVoPage = softService.page(soft,page);
        return Response.build(ResponseCode.QUERY_SUCCESS,softVoPage);
    }

    @ApiOperation(value = "增加软件-需要验证api")
    @RspHandle
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Response create(Soft soft) {

        boolean b = softService.save(soft);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @ApiOperation(value = "修改软件-需要验证api")
    @RspHandle
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Response update(Soft soft) {

        boolean b = softService.updateById(soft);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @ApiOperation(value = "删除软件-需要验证api")
    @RspHandle
    @RequestMapping(value = "remove",method = RequestMethod.POST)
    @ResponseBody
    public Response remove(String softId) {

        boolean b = softService.removeById(softId);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

}
