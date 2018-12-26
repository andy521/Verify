package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.AccountRegisterLogMapper;
import com.orange.verify.api.bean.AccountRegisterLog;
import com.orange.verify.api.service.AccountRegisterLogService;

import java.util.List;

@Service
public class AccountRegisterLogImpl extends ServiceImpl<AccountRegisterLogMapper, AccountRegisterLog>
        implements AccountRegisterLogService {

    @Override
    public List<String> getBeforeData() {

        return super.baseMapper.getBeforeData();
    }

}
