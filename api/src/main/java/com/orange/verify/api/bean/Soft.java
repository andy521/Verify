package com.orange.verify.api.bean;

import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;

/**
 * 软件
 * t_soft
 * @author Orange
 * @date 2019/01/17
 */
@TableName("t_soft")
@KeySequence("SEQ_TEST")
public class Soft implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private Long createDate;

    @TableLogic
    private Integer delFlag;

    /**
     * 备注
     * remarks
     */
    private String remarks;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private Long updateDate;

    /**
     * 换绑策略 0.支持换绑定 1.不支持换绑定
     * change_strategy
     */
    private Integer changeStrategy;

    /**
     * 多开策略 0.只支持单机 1.无限制
     * dosing_strategy
     */
    private Integer dosingStrategy;

    /**
     * 被通知的邮箱账户名
     * email_name
     */
    private String emailName;

    /**
     * 软件被留言 是否邮件通知 0.通知 1.不通知
     * email_notificatio
     */
    private Integer emailNotificatio;

    /**
     * 软件名称
     * name
     */
    private String name;

    /**
     * 公告
     * notice
     */
    private String notice;

    /**
     * 关闭注册后的返回信息
     * registe_close_msg
     */
    private String registeCloseMsg;

    /**
     * 注册状态 0.开放注册 1.关闭注册
     * register_status
     */
    private Integer registerStatus;

    /**
     * 关闭状态下的返回信息
     * service_close_msg
     */
    private String serviceCloseMsg;

    /**
     * 服务状态 0.收费 1.免费开放 2.关闭开放使用
     * service_status
     */
    private Integer serviceStatus;

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getChangeStrategy() {
        return changeStrategy;
    }

    public void setChangeStrategy(Integer changeStrategy) {
        this.changeStrategy = changeStrategy;
    }

    public Integer getDosingStrategy() {
        return dosingStrategy;
    }

    public void setDosingStrategy(Integer dosingStrategy) {
        this.dosingStrategy = dosingStrategy;
    }

    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName == null ? null : emailName.trim();
    }

    public Integer getEmailNotificatio() {
        return emailNotificatio;
    }

    public void setEmailNotificatio(Integer emailNotificatio) {
        this.emailNotificatio = emailNotificatio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
    }

    public String getRegisteCloseMsg() {
        return registeCloseMsg;
    }

    public void setRegisteCloseMsg(String registeCloseMsg) {
        this.registeCloseMsg = registeCloseMsg == null ? null : registeCloseMsg.trim();
    }

    public Integer getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(Integer registerStatus) {
        this.registerStatus = registerStatus;
    }

    public String getServiceCloseMsg() {
        return serviceCloseMsg;
    }

    public void setServiceCloseMsg(String serviceCloseMsg) {
        this.serviceCloseMsg = serviceCloseMsg == null ? null : serviceCloseMsg.trim();
    }

    public Integer getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}