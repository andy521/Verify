package com.orange.verify.api;

import com.orange.verify.common.BaseEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 软件版本控制
 */
@Entity(name = "t_soft_versions")
@Table(appliesTo = "t_soft_versions",comment = "软件版本控制")
public class SoftVersions extends BaseEntity {

    @Column(columnDefinition = "varchar(100) comment '软件绑定id'")
    private String softId;

    @Column(columnDefinition = "varchar(100) comment '版本号'")
    private String number;

    @Column(columnDefinition = "int(2) default 0 comment '是否强制更新 0.不强制 1.强制'")
    private Integer novatioNecessaria;

    @Column(columnDefinition = "varchar(255) default '' comment '更新url'")
    private String updateUrl;

    @Column(columnDefinition = "varchar(255) default '' comment '更新公告'")
    private String notice;

}
