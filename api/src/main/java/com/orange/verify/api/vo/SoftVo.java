package com.orange.verify.api.vo;

import com.orange.verify.api.bean.Soft;
import lombok.Data;

@Data
public class SoftVo extends Soft {

    //软件用户数量
    private Long accountTotal;

    //最新版本
    private String versionsNum;

    private Long leaveMessageNum;

}
