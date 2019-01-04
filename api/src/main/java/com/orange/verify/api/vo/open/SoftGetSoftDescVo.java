package com.orange.verify.api.vo.open;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
public class SoftGetSoftDescVo implements Serializable {

    @NotBlank(message = "软件id不能为空")
    private String softId;

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

}
