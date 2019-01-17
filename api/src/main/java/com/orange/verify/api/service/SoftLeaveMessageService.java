package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.SoftLeaveMessage;
import com.orange.verify.api.sr.ServiceResult;
import com.orange.verify.api.vo.SoftLeaveMessageVo;
import com.orange.verify.api.vo.open.SoftLeaveMeesageSubmitVo;

public interface SoftLeaveMessageService extends IService<SoftLeaveMessage> {

    Page<SoftLeaveMessageVo> page(SoftLeaveMessageVo softLeaveMessageVo, Page page);

    ServiceResult create(SoftLeaveMeesageSubmitVo softLeaveMeesageSubmitVo);

}
