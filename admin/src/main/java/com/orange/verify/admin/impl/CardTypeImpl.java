package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.CardTypeMapper;
import com.orange.verify.api.bean.CardType;
import com.orange.verify.api.service.CardTypeService;
import com.orange.verify.api.vo.CardTypeVo;

@Service
public class CardTypeImpl extends ServiceImpl<CardTypeMapper, CardType> implements CardTypeService {


    @Override
    public Page<CardTypeVo> page(CardType cardType, Page page) {

        return page.setRecords(super.baseMapper.page(cardType,page));
    }

}
