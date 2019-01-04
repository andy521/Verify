package com.orange.verify.api;

import com.orange.verify.common.BaseEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 平台管理用户
 * 必须满足三个条件才可登陆
 * flag = 另外的条件标志
 */
@Entity(name = "t_user")
@Table(appliesTo = "t_user",comment = "平台管理用户")
public class User extends BaseEntity {

    @Column(columnDefinition = "varchar(100) comment '管理用户名'")
    private String username;

    @Column(columnDefinition = "varchar(100) comment '管理用户密码'")
    private String password;

    @Column(columnDefinition = "varchar(100) comment '管理标识符'")
    private String flag;

}
