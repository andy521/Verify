package com.orange.verify.api.sr;

import java.io.Serializable;

public class SoftLeaveMessageImplCreateEnum implements Serializable {

    public static final int LEAVE_MESSAGE_SEND_SUCCESS = 1;

    public static final int SOFT_EMPTY = 2;

    public static final int SOFT_CLOSE = 3;

    public static final int BAIDU_API_ERROR = 4;

    public static final int LEAVE_MESSAGE_SEND_ERROR = 5;

}
