package com.orange.verify.api;

import com.orange.verify.common.BaseEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 软件充值卡密
 */
@Entity(name = "t_card")
@Table(appliesTo = "t_card",comment = "软件充值卡")
public class Card extends BaseEntity {

    @Column(nullable = false,columnDefinition = "varchar(100) comment '软件绑定id'")
    private String softId;

    @Column(columnDefinition = "varchar(100) comment '用户绑定id'")
    private String accountId;

    @Column(nullable = false,columnDefinition = "varchar(100) comment '卡类绑定id'")
    private String cardTypeId;

    @Column(nullable = false,columnDefinition = "varchar(255) comment '充值卡号'")
    private String cardNumber;

    @Column(columnDefinition = "int(2) default 0 comment '使用状态 0.不使用 1.使用'")
    private Integer useStatus;

    @Column(columnDefinition = "int(2) default 0 comment '是否封停使用 0.不是 1.是的'")
    private Integer closure;

}
