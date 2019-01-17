package com.orange.verify.api;

import com.orange.verify.common.BaseEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;

/**
 * 用户注册日志
 */
@Entity(name = "t_account_register_log")
@Table(appliesTo = "t_account_register_log",comment = "用户注册日志")
public class AccountRegisterLog extends BaseEntity {

    private String accountId;

    private String ip;

    private String ipInfo;

    private String softId;

}
