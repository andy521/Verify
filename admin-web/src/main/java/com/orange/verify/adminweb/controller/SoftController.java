package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.ParameterError;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.Soft;
import com.orange.verify.api.sr.ServiceResult;
import com.orange.verify.api.service.SoftService;
import com.orange.verify.api.sr.SoftImplGetSoftDescEnum;
import com.orange.verify.api.vo.SoftVo;
import com.orange.verify.api.vo.open.SoftGetSoftDescVo;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "soft")
public class SoftController extends BaseController {

    @Reference
    private SoftService softService;

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Response page(Soft soft, Page page) {

        Page<SoftVo> softVoPage = softService.page(soft,page);
        return Response.build(ResponseCode.QUERY_SUCCESS,softVoPage);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "count",method = RequestMethod.GET)
    @ResponseBody
    public Response count() {

        int count = softService.count();
        return Response.build(ResponseCode.QUERY_SUCCESS,count);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public Response list() {

        List<Soft> list = softService.list();
        return Response.build(ResponseCode.QUERY_SUCCESS,list);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "single",method = RequestMethod.GET)
    @ResponseBody
    public Response single(String softId) {

        Soft soft = softService.getById(softId);
        return Response.build(ResponseCode.QUERY_SUCCESS,soft);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Response create(Soft soft) {

        boolean b = softService.save(soft);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Response update(Soft soft) {

        boolean b = softService.updateById(soft);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "remove",method = RequestMethod.POST)
    @ResponseBody
    public Response remove(String softId) {

        boolean b = softService.removeById(softId);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @RspHandle
    @RequestMapping(value = "getSoftDesc",method = RequestMethod.POST)
    @ResponseBody
    public Response getSoftDesc(@Validated SoftGetSoftDescVo accountGetSoftDescVo, BindingResult result)
            throws ParameterError {

        super.parametric(result);

        ServiceResult<SoftGetSoftDescVo> getSoftDesc = softService.getSoftDesc(accountGetSoftDescVo);
        switch (getSoftDesc.getCode()) {
            case SoftImplGetSoftDescEnum.SUCCESS:
                return Response.build(ResponseCode.QUERY_SUCCESS,getSoftDesc.getData());

            case SoftImplGetSoftDescEnum.SOFT_CLOSE:
                return Response.build(ResponseCode.SOFT_CLOSE);

            case SoftImplGetSoftDescEnum.SOFT_EMPTY:
                return Response.build(ResponseCode.SOFT_EMPTY);
                
            default:
                return Response.build(ResponseCode.UNKNOWN_ERROR);
        }
    }


}
