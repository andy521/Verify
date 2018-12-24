package com.orange.verify.api;

import com.orange.verify.common.BaseEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 百度地理api配置
 * 通过ip拿到ip地理信息
 */
@Entity(name = "t_baidu_map_api")
@Table(appliesTo = "t_baidu_map_api",comment = "百度地理api配置")
public class BaiduMapApi extends BaseEntity {

    @Column(columnDefinition = "varchar(100) comment 'appkey'")
    private String appkey;

}
