package com.orange.verify.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orange.verify.api.bean.AccountRegisterLog;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AccountRegisterLogMapper extends BaseMapper<AccountRegisterLog> {

    //找出以往7天到现在的数据
    @Select("SELECT FROM_UNIXTIME(ROUND(create_date / 1000)) AS TIME FROM t_account_register_log\n" +
            "WHERE \n" +
            "TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(create_date/1000)) >= 0\n" +
            "AND\n" +
            "TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(create_date/1000)) <= 7\n" +
            "ORDER BY create_date ASC")
    List<String> getBeforeData();

}
