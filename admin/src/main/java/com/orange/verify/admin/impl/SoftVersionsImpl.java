package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.SoftMapper;
import com.orange.verify.admin.mapper.SoftVersionsMapper;
import com.orange.verify.api.bean.Soft;
import com.orange.verify.api.bean.SoftVersions;
import com.orange.verify.api.service.SoftVersionsService;
import com.orange.verify.api.vo.SoftVersionsVo;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SoftVersionsImpl extends ServiceImpl<SoftVersionsMapper, SoftVersions> implements SoftVersionsService {

    @Autowired
    private SoftMapper softMapper;

    @Override
    public SoftVersionsVo getSingleBySoftId(String softId) {

        return super.baseMapper.getSingleBySoftId(softId);
    }

    @Override
    public boolean saveLogic(SoftVersions softVersions) {

        //去取软件是否存在
        Soft soft = softMapper.selectById(softVersions.getSoftId());
        if (soft == null) {
            return false;
        }

        // 去取软件id有没有在版本表里面
        // 有 >>> 新增失败 没有 >>> 做新增
        int count = super.count(new QueryWrapper<SoftVersions>().eq("soft_id",softVersions.getSoftId()));
        if (count > 0) {
            return false;
        }

        return super.save(softVersions);
    }

    @Override
    public com.orange.verify.api.vo.open.SoftVersionsVo getVersions(String softId) {

        return super.baseMapper.getVersions(softId);
    }

}
