package com.orange.verify.admin.transition;

import com.orange.verify.api.bean.Account;
import com.orange.verify.api.bean.InterfaceManagement;
import com.orange.verify.api.bean.Soft;
import com.orange.verify.api.bean.SoftLeaveMessage;
import com.orange.verify.api.vo.open.SoftGetSoftDescVo;
import com.orange.verify.api.vo.open.AccountRegisterVo;
import com.orange.verify.api.vo.open.SoftLeaveMeesageSubmitVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface Transition {

    @Mapping(target = "createIp",source = "ip")
    Account fromVo(AccountRegisterVo accountRegisterVo);

    SoftLeaveMessage fromVo(SoftLeaveMeesageSubmitVo softLeaveMeesageSubmitVo);

    SoftGetSoftDescVo toVo(Soft soft);

}
