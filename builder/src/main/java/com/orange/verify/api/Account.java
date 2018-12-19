package com.orange.verify.api;

import com.orange.verify.common.BaseEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 软件用户表
 * 每个软件对应一批用户
 * 软件和用户之间是一对多的关系...
 */
@Entity(name = "t_account")
@Table(appliesTo = "t_account",comment = "软件用户表")
public class Account extends BaseEntity {

    @Column(nullable = false,columnDefinition = "varchar(100) comment '软件绑定id'")
    private String softId;

    @Column(nullable = false,columnDefinition = "varchar(100) comment '卡密绑定id'")
    private String cardId;

    @Column(nullable = false,columnDefinition = "varchar(10) comment '用户名'")
    private String username;

    @Column(nullable = false,columnDefinition = "varchar(10) comment '用户密码'")
    private String password;

    @Column(nullable = false,columnDefinition = "varchar(100) comment '用户电脑的机器码'")
    private String code;

    @Column(columnDefinition = "varchar(20) comment '用户的联系QQ'")
    private String qq;

    @Column(columnDefinition = "varchar(20) comment '用户的联系手机号'")
    private String phoneNumber;

    @Column(columnDefinition = "varchar(10) comment '用户的真实姓名'")
    private String name;

    @Column(nullable = false,columnDefinition = "varchar(20) comment '用户注册的时候ip地址'")
    private String createIp;

    @Column(nullable = false,columnDefinition = "varchar(255) comment '创建时候的ip信息'")
    private String createIpInfo;

    @Column(columnDefinition = "int(2) default 0 comment '是否加入了黑名单 0.不是 1.是的'")
    private Integer blacklist;

}
