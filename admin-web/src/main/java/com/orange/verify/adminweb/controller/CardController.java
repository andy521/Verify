package com.orange.verify.adminweb.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.adminweb.annotation.RspHandle;
import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import com.orange.verify.api.bean.Card;
import com.orange.verify.api.bean.CardType;
import com.orange.verify.api.service.CardService;
import com.orange.verify.api.vo.CardTypeVo;
import com.orange.verify.api.vo.CardVo;
import com.orange.verify.api.vo.open.CardTimeLimitVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(description = "卡密")
@Controller
@RequestMapping(value = "card")
public class CardController {

    @Reference
    private CardService cardService;

    @ApiOperation(value = "获取分页卡密类型-需要验证api")
    @RspHandle
    @RequestMapping(value = "page",method = RequestMethod.GET)
    @ResponseBody
    public Response page(CardVo cardVo, Page page) {

        Page<CardVo> cardVoPage = cardService.page(cardVo,page);
        return Response.build(ResponseCode.QUERY_SUCCESS,cardVoPage);
    }

    @ApiOperation(value = "获取卡密数量-需要验证api")
    @RspHandle
    @RequestMapping(value = "count",method = RequestMethod.GET)
    @ResponseBody
    public Response count() {

        int count = cardService.count();
        return Response.build(ResponseCode.QUERY_SUCCESS,count);
    }

    @ApiOperation(value = "增加卡密-需要验证api")
    @RspHandle
    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    public Response create(Card card,Integer count) throws Exception {

        if (count > 100) {
            return Response.build(ResponseCode.PRODUCTION_TOO_MUCH);
        }

        boolean b = cardService.saveLogic(card,count);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @ApiOperation(value = "封停卡密-需要验证api")
    @RspHandle
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

    @ApiOperation(value = "删除卡密-需要验证api")
    @RspHandle
    @RequestMapping(value = "remove",method = RequestMethod.POST)
    @ResponseBody
    public Response remove(String cardId) {

        boolean b = cardService.removeById(cardId);
        if (b == true) {
            return Response.success();
        }
        return Response.error();
    }

    @ApiOperation(value = "查询卡密使用期限-开放接口")
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
