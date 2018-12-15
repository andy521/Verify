package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.SoftVersions;
import com.orange.verify.api.service.SoftVersionsService;
import com.orange.verify.api.vo.SoftVersionsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(description = "软件版本")
@Controller
@RequestMapping(value = "softVersions")
public class SoftVersionsController {

    @Reference
    private SoftVersionsService softVersionsService;

    @ApiOperation(value = "查询版本")
    @RspHandle
    @RequestMapping(value = "getSingleBySoftId",method = RequestMethod.GET)
    @ResponseBody
    public Response getSingleBySoftId(String softId) {

        SoftVersionsVo singleBySoftId = softVersionsService.getSingleBySoftId(softId);
        return Response.build(ResponseCode.QUERY_SUCCESS, singleBySoftId);
    }

    @ApiOperation(value = "创建版本")
    @RspHandle
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Response create(SoftVersions softVersions) {

        boolean b = softVersionsService.saveLogic(softVersions);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @ApiOperation(value = "修改版本")
    @RspHandle
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Response update(SoftVersions softVersions) {

        boolean b = softVersionsService.updateById(softVersions);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

}
