package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.Card;
import com.orange.verify.api.vo.CardVo;
import com.orange.verify.api.vo.open.CardTimeLimitVo;

import java.util.List;

public interface CardService extends IService<Card> {

    Page<CardVo> page(CardVo cardVo, Page page);

    void saveLogic(Card card,Integer count);

    CardTimeLimitVo getCardTimeLimit(String username,String password,String softId);

    void sell(List<String> sell);

    void batchRemove(List<String> cardList);

}
