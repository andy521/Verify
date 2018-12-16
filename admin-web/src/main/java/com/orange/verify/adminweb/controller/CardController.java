package com.orange.verify.adminweb.controller;

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
    public Response page(Card card, Page page) {

        Page<CardVo> cardVoPage = cardService.page(card,page);
        return Response.build(ResponseCode.QUERY_SUCCESS,cardVoPage);
    }

}
