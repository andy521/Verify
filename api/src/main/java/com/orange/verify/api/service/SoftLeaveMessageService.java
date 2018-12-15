package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.SoftLeaveMessage;

public interface SoftLeaveMessageService extends IService<SoftLeaveMessage> {

    Page<SoftLeaveMessage> page(String softId,Page page);

}
