package com.orange.verify.api.bean;

import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;

/**
 * 软件充值卡
 * t_card
 * @author Orange
 * @date 2018/12/03
 */
@TableName("t_card")
@KeySequence("SEQ_TEST")
public class Card implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private Long createDate;

    @TableLogic
    private Integer delFlag;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private Long updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }
}