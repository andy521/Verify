package com.orange.verify.api;

import com.orange.verify.common.BaseEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 软件留言
 */
@Entity(name = "t_soft_leave_message")
@Table(appliesTo = "t_soft_leave_message",comment = "软件留言")
public class SoftLeaveMessage extends BaseEntity {

    @Column(columnDefinition = "varchar(100) comment '软件绑定id'")
    private String softId;

    @Column(columnDefinition = "varchar(255) comment '用户留言内容'")
    private String content;

    @Column(columnDefinition = "varchar(100) comment '用户的QQ号'")
    private String qq;

    private String ip;

    private String ipInfo;

}
