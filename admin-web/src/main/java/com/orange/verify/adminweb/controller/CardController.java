package com.orange.verify.adminweb.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.Card;
import com.orange.verify.api.service.CardService;
import com.orange.verify.api.vo.CardVo;
import com.orange.verify.api.vo.open.CardTimeLimitVo;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "card")
public class CardController {

    @Reference
    private CardService cardService;

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Response page(CardVo cardVo, Page page) {

        Page<CardVo> cardVoPage = cardService.page(cardVo,page);
        return Response.build(ResponseCode.QUERY_SUCCESS,cardVoPage);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "count",method = RequestMethod.GET)
    @ResponseBody
    public Response count() {

        int count = cardService.count();
        return Response.build(ResponseCode.QUERY_SUCCESS,count);
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Response create(Card card,Integer count) {

        if (count > 100) {
            return Response.build(ResponseCode.PRODUCTION_TOO_MUCH);
        }

        cardService.saveLogic(card,count);

        return Response.success();
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "closure",method = RequestMethod.POST)
    @ResponseBody
    public Response closure(String cardId,Integer closure) {

        Card card = new Card();
        card.setId(cardId);
        card.setClosure(closure);
        boolean b = cardService.updateById(card);

        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "sellStatus",method = RequestMethod.POST)
    @ResponseBody
    public Response sellStatus(String cardId,Integer sellStatus) {

        Card card = new Card();
        card.setId(cardId);
        card.setSellStatus(sellStatus);
        boolean b = cardService.updateById(card);

        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "sell",method = RequestMethod.POST)
    @ResponseBody
    public Response sell(@RequestBody List<String> sell) {

        cardService.sell(sell);

        return Response.success();
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "batchRemove",method = RequestMethod.POST)
    @ResponseBody
    public Response batchRemove(@RequestBody List<String> cardList) {

        cardService.batchRemove(cardList);

        return Response.success();
    }

    @RspHandle
    @RequiresUser
    @RequestMapping(value = "remove",method = RequestMethod.POST)
    @ResponseBody
    public Response remove(String cardId) {

        boolean b = cardService.removeById(cardId);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @RspHandle
    @RequestMapping(value = "getCardTimeLimit",method = RequestMethod.POST)
    @ResponseBody
    public Response getCardTimeLimit(String username,String password,String softId) {

        if (StrUtil.hasEmpty(username,password,softId)) {
            return Response.build(ResponseCode.PARAMETER_ERROR);
        }

        CardTimeLimitVo cardTimeLimit = cardService.getCardTimeLimit(username, password, softId);
        if (cardTimeLimit == null) {
            return Response.build(ResponseCode.CARD_EMPTY);
        }

        return Response.build(ResponseCode.QUERY_SUCCESS,cardTimeLimit);
    }

}
