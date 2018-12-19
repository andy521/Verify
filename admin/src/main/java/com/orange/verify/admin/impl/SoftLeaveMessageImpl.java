package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.SoftLeaveMessageMapper;
import com.orange.verify.api.bean.SoftLeaveMessage;
import com.orange.verify.api.service.SoftLeaveMessageService;
import com.orange.verify.api.vo.SoftLeaveMessageVo;

@Service
public class SoftLeaveMessageImpl extends ServiceImpl<SoftLeaveMessageMapper, SoftLeaveMessage> implements SoftLeaveMessageService {


    @Override
    public Page<SoftLeaveMessage> page(SoftLeaveMessageVo softLeaveMessageVo, Page page) {

        return page.setRecords(super.baseMapper.page(softLeaveMessageVo,page));
    }

}
