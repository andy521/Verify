package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.SoftMapper;
import com.orange.verify.admin.transition.Transition;
import com.orange.verify.api.bean.Soft;
import com.orange.verify.api.sc.SoftServiceStatus;
import com.orange.verify.api.sr.ServiceResult;
import com.orange.verify.api.service.SoftService;
import com.orange.verify.api.sr.SoftImplGetSoftDescEnum;
import com.orange.verify.api.vo.SoftVo;
import com.orange.verify.api.vo.open.SoftGetSoftDescVo;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SoftImpl extends ServiceImpl<SoftMapper, Soft> implements SoftService {

    @Autowired
    private Transition transition;

    @Override
    public Page<SoftVo> page(Soft soft, Page page) {
        return page.setRecords(baseMapper.page(soft,page));
    }

    @Override
    public ServiceResult<SoftGetSoftDescVo> getSoftDesc(SoftGetSoftDescVo accountGetSoftDescVo) {

        ServiceResult<SoftGetSoftDescVo> result = new ServiceResult<>();

        Soft soft = super.baseMapper.selectById(accountGetSoftDescVo.getSoftId());
        //软件不存在直接返回
        if (soft == null) {
            result.setCode(SoftImplGetSoftDescEnum.SOFT_EMPTY);
            return result;
        } else if (soft.getServiceStatus() == SoftServiceStatus.CLOSE.getStatusCode()) {
            result.setCode(SoftImplGetSoftDescEnum.SOFT_CLOSE);
            result.setMsg(soft.getServiceCloseMsg());
            return result;
        }

        SoftGetSoftDescVo softGetSoftDescVo = transition.toVo(soft);
        result.setCode(SoftImplGetSoftDescEnum.SUCCESS);
        result.setData(softGetSoftDescVo);

        return result;
    }

}
