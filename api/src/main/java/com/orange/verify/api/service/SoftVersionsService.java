package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.SoftVersions;
import com.orange.verify.api.vo.SoftVersionsVo;

public interface SoftVersionsService extends IService<SoftVersions> {

    SoftVersionsVo getSingleBySoftId(String softId);

    boolean saveLogic(SoftVersions softVersions);

}
