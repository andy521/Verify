package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.Soft;
import com.orange.verify.api.sr.ServiceResult;
import com.orange.verify.api.vo.SoftVo;
import com.orange.verify.api.vo.open.SoftGetSoftDescVo;

public interface SoftService extends IService<Soft> {

    Page<SoftVo> page(Soft soft,Page page);

    ServiceResult<SoftGetSoftDescVo> getSoftDesc(SoftGetSoftDescVo accountGetSoftDescVo);

}
