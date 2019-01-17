package com.orange.verify.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orange.verify.api.bean.BaiduMapApi;
import org.apache.ibatis.annotations.Select;

public interface BaiduMapApiMapper extends BaseMapper<BaiduMapApi> {

    @Select("select count(*) from t_baidu_map_api")
    int getBaiduMapApiCount();

    @Select("select * from t_baidu_map_api limit 1")
    BaiduMapApi getSingle();

}
