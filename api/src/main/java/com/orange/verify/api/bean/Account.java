package com.orange.verify.api.bean;

import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;

/**
 * 软件用户表
 * t_account
 * @author Orange
 * @date 2018/12/03
 */
@TableName("t_account")
@KeySequence("SEQ_TEST")
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @TableField(value = "create_date",fill = FieldFill.INSERT)
    private Long createDate;

    @TableLogic
    private Integer delFlag;

    @TableField(value = "update_date",fill = FieldFill.UPDATE)
    private Long updateDate;

    /**
     * 用户电脑的机器码
     * code
     */
    private String code;

    /**
     * 用户注册的时候ip地址
     * create_ip
     */
    private String createIp;

    /**
     * 创建时候的ip信息
     * create_ip_info
     */
    private String createIpInfo;

    /**
     * 用户的真实姓名
     * name
     */
    private String name;

    /**
     * 用户密码
     * password
     */
    private String password;

    /**
     * 用户的联系手机号
     * phone_number
     */
    private String phoneNumber;

    /**
     * 用户的联系QQ
     * qq
     */
    private String qq;

    /**
     * 软件绑定id
     * soft_id
     */
    private String softId;

    /**
     * 用户名
     * username
     */
    private String username;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp == null ? null : createIp.trim();
    }

    public String getCreateIpInfo() {
        return createIpInfo;
    }

    public void setCreateIpInfo(String createIpInfo) {
        this.createIpInfo = createIpInfo == null ? null : createIpInfo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getSoftId() {
        return softId;
    }

    public void setSoftId(String softId) {
        this.softId = softId == null ? null : softId.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
}