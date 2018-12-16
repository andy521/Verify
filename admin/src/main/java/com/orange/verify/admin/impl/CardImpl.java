package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.CardMapper;
import com.orange.verify.api.bean.Card;
import com.orange.verify.api.service.CardService;
import com.orange.verify.api.vo.CardVo;

@Service
public class CardImpl extends ServiceImpl<CardMapper, Card> implements CardService {


    @Override
    public Page<CardVo> page(Card card, Page page) {

        return page.setRecords(super.baseMapper.page(card,page));
    }
}
