package com.orange.verify.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.api.bean.SoftVersions;
import com.orange.verify.api.vo.SoftVersionsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SoftVersionsMapper extends BaseMapper<SoftVersions> {

    @Select("SELECT sv.*,(SELECT s.name FROM t_soft s WHERE s.id = sv.soft_id) as soft_name " +
            "FROM t_soft_versions sv " +
            "where sv.del_flag = 0 " +
            "<if test=\"softId != null and softId != ''\"> and sv.soft_id = #{softId} </if>")
    List<SoftVersionsVo> getPageBySoftId(@Param("softId") String softId, Page page);

}