package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.SoftLeaveMessageMapper;
import com.orange.verify.api.bean.SoftLeaveMessage;
import com.orange.verify.api.service.SoftLeaveMessageService;

@Service
public class SoftLeaveMessageImpl extends ServiceImpl<SoftLeaveMessageMapper, SoftLeaveMessage> implements SoftLeaveMessageService {


    @Override
    public Page<SoftLeaveMessage> page(String softId,Page page) {

        return page.setRecords(super.baseMapper.page(softId,page));
    }

}
