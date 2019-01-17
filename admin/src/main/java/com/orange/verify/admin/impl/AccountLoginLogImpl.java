package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.AccountLoginLogMapper;
import com.orange.verify.api.bean.AccountLoginLog;
import com.orange.verify.api.service.AccountLoginLogService;
import com.orange.verify.api.vo.AccountLoginLogVo;

import java.util.List;

@Service
public class AccountLoginLogImpl extends ServiceImpl<AccountLoginLogMapper, AccountLoginLog>
        implements AccountLoginLogService {


    @Override
    public List<String> getBeforeData(String softId) {

        return super.baseMapper.getBeforeData(softId);
    }

    @Override
    public Page<AccountLoginLogVo> page(AccountLoginLogVo accountLoginLog, Page page) {

        return page.setRecords(super.baseMapper.page(accountLoginLog,page));
    }

}
