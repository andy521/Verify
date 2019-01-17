package com.orange.verify.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.api.bean.AccountLoginLog;
import com.orange.verify.api.vo.AccountLoginLogVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AccountLoginLogMapper extends BaseMapper<AccountLoginLog> {

    @Select("<script>" +
            "SELECT FROM_UNIXTIME(ROUND(create_date / 1000)) AS TIME FROM t_account_login_log " +
            "WHERE " +
            "<![CDATA[ TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(create_date/1000)) >= 0 " +
            "AND " +
            "TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(create_date/1000)) <= 7 ]]>" +
            "<if test=\"softId != null and softId != ''\"> and soft_id = #{softId} </if>" +
            "ORDER BY create_date ASC" +
            "</script>")
    List<String> getBeforeData(@Param("softId") String softId);

    @Delete("delete from t_account_login_log\n" +
            "where TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(create_date/1000)) >= 10\n" +
            "limit 100")
    int deleteLog();

    @Select("<script>" +
            "select tall.*," +
            "(SELECT s.name FROM t_soft s WHERE s.id = tall.soft_id) as soft_name," +
            "(SELECT a.username FROM t_account a WHERE a.id = tall.account_id) as account_name " +
            "from t_account_login_log tall " +
            "<where>" +
            "<if test=\"accountLoginLog.softId != null and accountLoginLog.softId != ''\">" +
            " and tall.soft_id = #{accountLoginLog.softId} " +
            "</if>" +
            "</where>" +
            "ORDER BY create_date desc" +
            "</script>")
    List<AccountLoginLogVo> page(@Param("accountLoginLog") AccountLoginLogVo accountLoginLog, Page page);

}
