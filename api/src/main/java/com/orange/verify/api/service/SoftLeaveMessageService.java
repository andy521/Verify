package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.SoftLeaveMessage;
import com.orange.verify.api.vo.SoftLeaveMessageVo;

public interface SoftLeaveMessageService extends IService<SoftLeaveMessage> {

    Page<SoftLeaveMessage> page(SoftLeaveMessageVo softLeaveMessageVo, Page page);

}
