package com.orange.verify.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orange.verify.api.bean.AccountLoginLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AccountLoginLogMapper extends BaseMapper<AccountLoginLog> {

    //找出以往7天到现在的数据
    @Select("SELECT FROM_UNIXTIME(ROUND(create_date / 1000)) AS TIME FROM t_account_login_log\n" +
            "WHERE \n" +
            "TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(create_date/1000)) >= 0\n" +
            "AND\n" +
            "TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(create_date/1000)) <= 7\n" +
            "ORDER BY create_date ASC")
    List<String> getBeforeData();

    @Delete("delete from t_account_login_log\n" +
            "where TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(create_date/1000)) >= 10\n" +
            "limit 100")
    int deleteLog();

}
