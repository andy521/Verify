package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.CardType;
import com.orange.verify.api.vo.CardTypeVo;

public interface CardTypeService extends IService<CardType> {

    Page<CardTypeVo> page(CardType cardType, Page page);

}
