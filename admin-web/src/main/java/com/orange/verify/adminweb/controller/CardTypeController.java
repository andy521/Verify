package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.CardType;
import com.orange.verify.api.service.CardTypeService;
import com.orange.verify.api.vo.CardTypeVo;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "cardType")
public class CardTypeController {

    @Reference
    private CardTypeService cardTypeService;

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Response page(CardType cardType, Page page) {

        Page<CardTypeVo> cardTypeVoPage = cardTypeService.page(cardType,page);
        return Response.build(ResponseCode.QUERY_SUCCESS,cardTypeVoPage);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public Response list() {

        List<CardType> list = cardTypeService.list();
        return Response.build(ResponseCode.QUERY_SUCCESS,list);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "single",method = RequestMethod.GET)
    @ResponseBody
    public Response single(String cardTypeId) {

        CardType cardType = cardTypeService.getById(cardTypeId);
        return Response.build(ResponseCode.QUERY_SUCCESS,cardType);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Response create(CardType cardType) {

        boolean b = cardTypeService.save(cardType);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Response update(CardType cardType) {

        boolean b = cardTypeService.updateById(cardType);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @RspHandle
    @RequiresUser
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
