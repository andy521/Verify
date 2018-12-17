package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.CardMapper;
import com.orange.verify.api.bean.Card;
import com.orange.verify.api.service.CardService;
import com.orange.verify.api.vo.CardVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CardImpl extends ServiceImpl<CardMapper, Card> implements CardService {


    @Override
    public Page<CardVo> page(CardVo cardVo, Page page) {

        return page.setRecords(super.baseMapper.page(cardVo,page));
    }

    @Override
    public boolean saveLogic(Card card, Integer count) {

        for (int i = 0;i < count;i++) {
            Card cardSave = new Card();
            cardSave.setCardTypeId(card.getCardTypeId());
            cardSave.setCardNumber(UUID.randomUUID().toString().replaceAll("-",""));
            super.baseMapper.insert(cardSave);
        }

        return true;
    }
}
