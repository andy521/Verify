package com.orange.verify.api.vo.open;

import lombok.Data;

import java.io.Serializable;

@Data
public class CardTimeLimitVo implements Serializable {

    private Long startDate;

    private Long endDate;

}
