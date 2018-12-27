package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.BaiduMapApi;
import com.orange.verify.api.service.BaiduMapApiService;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "baiduMapApi")
public class BaiduMapApiController {

    @Reference
    private BaiduMapApiService baiduMapApiService;

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "single",method = RequestMethod.GET)
    @ResponseBody
    public Response single() {

        List<BaiduMapApi> list = baiduMapApiService.list();
        if (list == null || list.size() == 0) {
            return Response.build(ResponseCode.EMPTY);
        }

        return Response.build(ResponseCode.QUERY_SUCCESS,list.get(0));
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Response create(BaiduMapApi baiduMapApi) {

        boolean b = baiduMapApiService.create(baiduMapApi);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Response update(BaiduMapApi baiduMapApi) {

        boolean b = baiduMapApiService.updateById(baiduMapApi);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }



}


