package com.orange.verify.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.api.bean.CardType;
import com.orange.verify.api.vo.CardTypeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CardTypeMapper extends BaseMapper<CardType> {

    @Select("<script>" +
            "select ct.*,(select name from t_soft where id = ct.soft_id) as soft_name from t_card_type ct " +
            "where ct.del_flag = 0 " +
            "<if test=\"cardType.softId != null and cardType.softId != ''\"> and ct.soft_id = #{cardType.softId} </if>" +
            "order by ct.create_date desc" +
            "</script>")
    List<CardTypeVo> page(@Param("cardType") CardType cardType, Page page);

}
