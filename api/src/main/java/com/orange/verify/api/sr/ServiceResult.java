package com.orange.verify.api.sr;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceResult<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

}
