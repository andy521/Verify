package com.orange.verify.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.api.bean.Card;
import com.orange.verify.api.vo.CardVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CardMapper extends BaseMapper<Card> {


    @Select("<script>" +
            "select c.*,(select name from t_soft where id = c.soft_id) as  from t_card c " +
            "where c.del_flag = 0 " +
            "<if test=\"card.softId != null \"> and c.soft_id = #{card.softId} </if>" +
            "<if test=\"card.closure != null \"> and c.closure = #{card.closure} </if>" +
            "</script>")
    List<CardVo> page(@Param("card") Card card, Page page);

}