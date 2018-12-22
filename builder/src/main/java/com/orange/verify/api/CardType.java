package com.orange.verify.api;

import com.orange.verify.common.BaseEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 软件充值卡类型
 */
@Entity(name = "t_card_type")
@Table(appliesTo = "t_card_type",comment = "软件充值卡类型")
public class CardType extends BaseEntity {

    @Column(columnDefinition = "varchar(100) comment '软件绑定id'")
    private String softId;

    @Column(columnDefinition = "int(2) default 0 comment '卡类单位 0.分 1.时 2.天 3.周 4.月 5.年'")
    private Integer unit;

    @Column(columnDefinition = "int comment '卡类值 比如对应的是分填1就是1分钟 以此类推'")
    private Integer value;

}
