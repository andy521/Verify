package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.InterfaceManagement;
import com.orange.verify.api.service.InterfaceManagementService;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "interfaceManagement")
public class InterfaceManagementController {

    @Reference
    private InterfaceManagementService interfaceManagementService;

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public Response list() {

        try {
            List<InterfaceManagement> all = interfaceManagementService.getAll();
            return Response.build(ResponseCode.QUERY_SUCCESS,all);
        } catch (Exception e) {
            return Response.build(ResponseCode.QUERY_ERROR);
        }
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "getSingle",method = RequestMethod.GET)
    @ResponseBody
    public Response getSingle(String key) {

        try {
            InterfaceManagement interfaceManagement = interfaceManagementService.getSingle(key);
            return Response.build(ResponseCode.QUERY_SUCCESS,interfaceManagement);
        } catch (Exception e) {
            return Response.build(ResponseCode.QUERY_ERROR);
        }
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "closeInterface",method = RequestMethod.POST)
    @ResponseBody
    public Response closeInterface(String key,Integer on) {

        try {

            if (on < 0 || on > 1) {
                return Response.build(ResponseCode.PARAMETER_ERROR);
            }

            interfaceManagementService.closeInterface(key,on);
            return Response.build(ResponseCode.SUCCESS);
        } catch (Exception e) {
            return Response.build(ResponseCode.ERROR);
        }
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "ipHandle",method = RequestMethod.POST)
    @ResponseBody
    public Response ipHandle(String key,Integer on) {

        try {

            if (on < 0 || on > 1) {
                return Response.build(ResponseCode.PARAMETER_ERROR);
            }

            interfaceManagementService.ipHandle(key,on);
            return Response.build(ResponseCode.SUCCESS);
        } catch (Exception e) {
            return Response.build(ResponseCode.ERROR);
        }
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Response update(InterfaceManagement interfaceManagement) {

        try {

            interfaceManagementService.update(interfaceManagement);
            return Response.build(ResponseCode.SUCCESS);
        } catch (Exception e) {
            return Response.build(ResponseCode.ERROR);
        }
    }

}
