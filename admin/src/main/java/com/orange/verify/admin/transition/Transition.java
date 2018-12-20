package com.orange.verify.admin.transition;

import com.orange.verify.api.bean.Account;
import com.orange.verify.api.vo.open.AccountRegisterVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface Transition {

    @Mapping(target = "createIp",source = "ip")
    Account fromVo(AccountRegisterVo accountRegisterVo);

}
