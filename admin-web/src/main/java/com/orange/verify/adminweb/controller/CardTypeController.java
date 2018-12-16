package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.CardType;
import com.orange.verify.api.service.CardTypeService;
import com.orange.verify.api.vo.CardTypeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(description = "卡类型")
@Controller
@RequestMapping(value = "cardType")
public class CardTypeController {

    @Reference
    private CardTypeService cardTypeService;

    @ApiOperation(value = "获取分页卡类型-需要验证api")
    @RspHandle
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Response page(CardType cardType, Page page) {

        Page<CardTypeVo> cardTypeVoPage = cardTypeService.page(cardType,page);
        return Response.build(ResponseCode.QUERY_SUCCESS,cardTypeVoPage);
    }

    @ApiOperation(value = "获取全部类型-需要验证api")
    @RspHandle
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public Response list() {

        List<CardType> list = cardTypeService.list();
        return Response.build(ResponseCode.QUERY_SUCCESS,list);
    }

    @ApiOperation(value = "获取单个-需要验证api")
    @RspHandle
    @RequestMapping(value = "single",method = RequestMethod.GET)
    @ResponseBody
    public Response single(String cardTypeId) {

        CardType cardType = cardTypeService.getById(cardTypeId);
        return Response.build(ResponseCode.QUERY_SUCCESS,cardType);
    }

    @ApiOperation(value = "增加卡-需要验证api")
    @RspHandle
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Response create(CardType cardType) {

        boolean b = cardTypeService.save(cardType);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @ApiOperation(value = "修改卡-需要验证api")
    @RspHandle
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Response update(CardType cardType) {

        boolean b = cardTypeService.updateById(cardType);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @ApiOperation(value = "删除卡-需要验证api")
    @RspHandle
    @RequestMapping(value = "remove",method = RequestMethod.POST)
    @ResponseBody
    public Response remove(String cardTypeId) {

        boolean b = cardTypeService.removeById(cardTypeId);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

}
