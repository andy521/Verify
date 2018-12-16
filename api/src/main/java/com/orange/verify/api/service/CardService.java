package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.Card;
import com.orange.verify.api.vo.CardVo;

public interface CardService extends IService<Card> {

    Page<CardVo> page(Card card, Page page);

}
