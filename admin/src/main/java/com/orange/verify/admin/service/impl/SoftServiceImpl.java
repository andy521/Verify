package com.orange.verify.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.SoftMapper;
import com.orange.verify.admin.service.SoftService;
import com.orange.verify.api.bean.Soft;
import com.orange.verify.api.vo.SoftVo;
import org.springframework.stereotype.Service;

//@Service
public class SoftServiceImpl extends ServiceImpl<SoftMapper, Soft> implements SoftService {

//    public Page<SoftVo> page(Soft soft, Page page) {
//        return page.setRecords(baseMapper.page(soft,page));
//    }

}
