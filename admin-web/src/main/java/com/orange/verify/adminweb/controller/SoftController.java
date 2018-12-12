package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.RspSetTime;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.Soft;
import com.orange.verify.api.service.SoftService;
import com.orange.verify.api.vo.SoftVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Log
@Api(description = "软件层")
@Controller
@RequestMapping(value = "soft")
public class SoftController {

    @Reference
    private SoftService softService;

    @ApiOperation(value = "获取分页软件-需要验证api")
    @RspSetTime
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Response page(Soft soft, Page page) {

        try {
            Page<SoftVo> softVoPage = softService.page(soft,page);
            return Response.build(ResponseCode.SUCCESS,softVoPage);
        }catch (Exception e) {
            log.info(e.getMessage());
            return Response.build(ResponseCode.UNKNOWN_ERROR);
        }
    }

    @ApiOperation(value = "增加软件-需要验证api")
    @RspSetTime
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Response create(Soft soft) {

        try {
            boolean b = softService.save(soft);
            if (b == true) {
                return Response.success();
            } else {
                return Response.error();
            }
        }catch (Exception e) {
            log.info(e.getMessage());
            return Response.build(ResponseCode.UNKNOWN_ERROR);
        }
    }

    @ApiOperation(value = "修改软件-需要验证api")
    @RspSetTime
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Response update(Soft soft) {

        try {
            boolean b = softService.updateById(soft);
            if (b == true) {
                return Response.success();
            } else {
                return Response.error();
            }
        }catch (Exception e) {
            log.info(e.getMessage());
            return Response.build(ResponseCode.UNKNOWN_ERROR);
        }
    }

    @ApiOperation(value = "删除软件-需要验证api")
    @RspSetTime
    @RequestMapping(value = "remove",method = RequestMethod.POST)
    @ResponseBody
    public Response remove(String softId) {

        try {
            boolean b = softService.removeById(softId);
            if (b == true) {
                return Response.success();
            } else {
                return Response.error();
            }
        }catch (Exception e) {
            log.info(e.getMessage());
            return Response.build(ResponseCode.UNKNOWN_ERROR);
        }
    }

}
