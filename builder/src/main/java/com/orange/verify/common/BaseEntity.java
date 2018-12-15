package com.orange.verify.common;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false,columnDefinition = "varchar(100) comment '主键'")
    private String id;

    @Column(columnDefinition = ("int(2) default 0 comment '删除判断'"))
    private int delFlag;

    @Column(columnDefinition = ("varchar(100) default null comment '备注'"))
    private String remarks;

    @Column(columnDefinition = ("bigint(20) default null comment '创建时间'"))
    private Long createDate;

    @Column(columnDefinition = ("bigint(20) default null comment '更新时间'"))
    private Long updateDate;

}
