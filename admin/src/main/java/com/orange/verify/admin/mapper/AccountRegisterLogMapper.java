package com.orange.verify.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.api.bean.AccountRegisterLog;
import com.orange.verify.api.vo.AccountRegisterLogVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AccountRegisterLogMapper extends BaseMapper<AccountRegisterLog> {

    //找出以往7天到现在的数据
    @Select("<script>" +
            "SELECT FROM_UNIXTIME(ROUND(create_date / 1000)) AS TIME FROM t_account_register_log " +
            "WHERE " +
            "<![CDATA[ TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(create_date/1000)) >= 0 " +
            "AND " +
            "TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(create_date/1000)) <= 7 ]]> " +
            "<if test=\"softId != null and softId != ''\"> and soft_id = #{softId} </if>" +
            "ORDER BY create_date ASC " +
            "</script>")
    List<String> getBeforeData(@Param("softId") String softId);

    @Delete("delete from t_account_register_log\n" +
            "where TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(create_date/1000)) >= 10\n" +
            "limit 100")
    int deleteLog();

    @Select("<script>" +
            "select tall.*," +
            "(SELECT s.name FROM t_soft s WHERE s.id = tall.soft_id) as soft_name," +
            "(SELECT a.username FROM t_account a WHERE a.id = tall.account_id) as account_name " +
            "from t_account_register_log tall " +
            "<where>" +
            "<if test=\"accountRegisterLog.softId != null and accountRegisterLog.softId != ''\">" +
            " and tall.soft_id = #{accountRegisterLog.softId} " +
            "</if>" +
            "</where>" +
            "ORDER BY create_date desc" +
            "</script>")
    List<AccountRegisterLogVo> page(
            @Param("accountRegisterLog") AccountRegisterLog accountRegisterLog, Page page);

}
