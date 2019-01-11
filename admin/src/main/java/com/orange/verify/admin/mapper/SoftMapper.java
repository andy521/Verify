package com.orange.verify.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.api.bean.Soft;
import com.orange.verify.api.vo.SoftVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SoftMapper extends BaseMapper<Soft> {

    @Select("<script>" +
            "SELECT " +
            "s.*, " +
            "(SELECT count(*) FROM t_account a where a.soft_id = s.id and a.del_flag=0 ) as account_total, " +
            "(SELECT sv.number FROM t_soft_versions sv WHERE sv.soft_id = s.id) as versions_num, " +
            "(SELECT count(*) FROM t_soft_leave_message slm WHERE slm.soft_id = s.id and slm.del_flag=0) as leave_message_num " +
            "FROM " +
            "t_soft s " +
            "where s.del_flag = 0 " +
            "<if test=\"soft.name != null and soft.name != ''\"> and s.name like concat('%',#{soft.name},'%') </if>" +
            "order by s.create_date desc" +
            "</script>")
    List<SoftVo> page(@Param("soft") Soft soft,Page page);

}