package com.orange.verify.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.api.bean.Account;
import com.orange.verify.api.vo.AccountVo;
import com.orange.verify.api.vo.open.AccountUpdatePasswordVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AccountMapper extends BaseMapper<Account> {

    @Select("<script>" +
            "SELECT a.*,s.name as soft_name FROM t_account a " +
            "LEFT JOIN t_soft s " +
            "ON a.soft_id = s.id " +
            "where a.del_flag = 0 " +
            "<if test=\"accountVo.softId != null and accountVo.softId != ''\"> and a.soft_id = #{accountVo.softId} </if>" +
            "<if test=\"accountVo.createIp != null and accountVo.createIp != ''\"> and a.create_ip = #{accountVo.createIp} </if>" +
            "<if test=\"accountVo.username != null and accountVo.username != ''\"> and a.username like concat('%',#{accountVo.username},'%') </if>" +
            "order by create_date desc" +
            "</script>")
    List<AccountVo> page(@Param("accountVo") AccountVo accountVo, Page page);

    @Update("update t_account set password = #{password} where " +
            "soft_id = #{softId} and " +
            "username = #{username} and " +
            "security_code = #{securityCode}")
    int updatePassword(AccountUpdatePasswordVo accountUpdatePasswordVo);

}