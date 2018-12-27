package com.orange.verify.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orange.verify.api.bean.EmailAccount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface EmailAccountMapper extends BaseMapper<EmailAccount> {

    @Select("SELECT * FROM t_email_account where is_use = 0 and del_flag = 0 ORDER BY total ASC LIMIT 0,1")
    EmailAccount get();

    @Update("UPDATE t_email_account SET total = total+1 WHERE id = #{id}")
    int use(@Param("id") String id);

}