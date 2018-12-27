package com.orange.verify.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.api.bean.SoftLeaveMessage;
import com.orange.verify.api.vo.SoftLeaveMessageVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SoftLeaveMessageMapper extends BaseMapper<SoftLeaveMessage> {

    @Select("<script>" +
            "select slm.*,s.name as soft_name from t_soft_leave_message slm " +
            "left join t_soft s " +
            "on slm.soft_id = s.id " +
            "where slm.del_flag = 0 " +
            "<if test=\"softLeaveMessageVo.softId != null and softLeaveMessageVo.softId != ''\"> and slm.soft_id = #{softLeaveMessageVo.softId} </if>" +
            "order by slm.create_date desc" +
            "</script>")
    List<SoftLeaveMessageVo> page(@Param("softLeaveMessageVo") SoftLeaveMessageVo softLeaveMessageVo, Page page);

}