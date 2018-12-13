package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.SoftMapper;
import com.orange.verify.api.bean.Soft;
import com.orange.verify.api.service.SoftService;
import com.orange.verify.api.vo.SoftVo;

@Service
public class SoftImpl extends ServiceImpl<SoftMapper, Soft> implements SoftService {

    @Override
    public Page<SoftVo> page(Soft soft, Page page) {
        return page.setRecords(baseMapper.page(soft,page));
    }

}
