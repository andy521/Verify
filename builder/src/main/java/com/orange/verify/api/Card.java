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

    @Column(columnDefinition = "varchar(100) comment '用户绑定id'")
    private String accountId;

    @Column(columnDefinition = "varchar(100) comment '卡类绑定id'")
    private String cardTypeId;

    @Column(columnDefinition = "varchar(255) comment '充值卡号'")
    private String cardNumber;

    @Column(columnDefinition = "int(2) default 0 comment '使用状态 0.未使用 1.已使用'")
    private Integer useStatus;

    @Column(columnDefinition = "int(2) default 0 comment '销售状态 0.未卖出 1.已卖出'")
    private Integer sellStatus;

    @Column(columnDefinition = "int(2) default 0 comment '是否封停使用 0.未封停 1.已封停'")
    private Integer closure;

    @Column(columnDefinition = "bigint comment '开始使用时间'")
    private Long startDate;

    @Column(columnDefinition = "bigint comment '结束时间'")
    private Long endDate;

}
