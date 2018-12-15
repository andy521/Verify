package com.orange.verify.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.api.bean.SoftLeaveMessage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SoftLeaveMessageMapper extends BaseMapper<SoftLeaveMessage> {

    @Select("select * from t_soft_leave_message where soft_id = #{softId} and del_flag = 0 order by create_date desc")
    List<SoftLeaveMessage> page(@Param("softId") String softId, Page page);

}