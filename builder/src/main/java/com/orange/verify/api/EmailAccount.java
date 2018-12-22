package com.orange.verify.api;

import com.orange.verify.common.BaseEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 邮箱账户
 * 用于通知 留言信息 给那个...
 */
@Entity(name = "t_email_account")
@Table(appliesTo = "t_email_account",comment = "邮箱账户")
public class EmailAccount extends BaseEntity {

    @Column(columnDefinition = "varchar(255) comment '用户名'")
    private String username;

    @Column(columnDefinition = "varchar(255) comment '用户密码'")
    private String password;

    @Column(columnDefinition = "int(2) default 0 comment '是否使用 0.使用 1.不使用'")
    private Integer isUse;

    @Column(columnDefinition = "bigint default 0 comment '被使用的次数'")
    private Long total;

}
