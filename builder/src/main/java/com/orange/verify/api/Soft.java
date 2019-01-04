package com.orange.verify.api;

import com.orange.verify.common.BaseEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 软件
 */
@Entity(name = "t_soft")
@Table(appliesTo = "t_soft",comment = "软件")
public class Soft extends BaseEntity {

    @Column(columnDefinition = "varchar(100) comment '软件名称'")
    private String name;

    @Column(columnDefinition = "int(2) default 0 comment '服务状态 0.收费 1.免费开放 2.关闭开放使用'")
    private Integer serviceStatus;

    @Column(columnDefinition = "varchar(255) default '' comment '关闭状态下的返回信息'")
    private String serviceCloseMsg;

    @Column(columnDefinition = "varchar(255) comment '公告'")
    private String notice;

    @Column(columnDefinition = "int(2) default 0 comment '注册状态 0.开放注册 1.关闭注册'")
    private Integer registerStatus;

    @Column(columnDefinition = "varchar(255) default '' comment '关闭注册后的返回信息'")
    private String registeCloseMsg;

    @Column(columnDefinition = "int(2) default 0 comment '多开策略 0.只支持单机 1.无限制'")
    private Integer dosingStrategy;

    @Column(columnDefinition = "int(2) default 0 comment '换绑策略 0.支持换绑定 1.不支持换绑定'")
    private Integer changeStrategy;

    @Column(columnDefinition = "int(2) default 0 comment '软件被留言 是否邮件通知 0.通知 1.不通知'")
    private Integer emailNotificatio;

    @Column(columnDefinition = "varchar(100) default '' comment '被通知的邮箱账户名'")
    private String emailName;

}
