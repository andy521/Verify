package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.AccountRegisterLogMapper;
import com.orange.verify.api.bean.AccountRegisterLog;
import com.orange.verify.api.service.AccountRegisterLogService;
import com.orange.verify.api.vo.AccountRegisterLogVo;

import java.util.List;

@Service
public class AccountRegisterLogImpl extends ServiceImpl<AccountRegisterLogMapper, AccountRegisterLog>
        implements AccountRegisterLogService {

    @Override
    public List<String> getBeforeData(String softId) {

        return super.baseMapper.getBeforeData(softId);
    }

    @Override
    public Page<AccountRegisterLogVo> page(AccountRegisterLog accountRegisterLog, Page page) {

        return page.setRecords(super.baseMapper.page(accountRegisterLog,page));
    }

}
