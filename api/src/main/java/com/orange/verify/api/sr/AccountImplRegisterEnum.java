package com.orange.verify.api.sr;

import java.io.Serializable;

public class AccountImplRegisterEnum implements Serializable {

    public static final int KEY_EMPTY = 1;

    public static final int SOFT_EMPTY = 2;

    public static final int BAIDU_API_ERROR = 3;

    public static final int KEY_ERROR = 4;

    public static final int ACCOUNT_ALREADY_EXIST = 5;

    public static final int PASSWORD_LENGTH_ERROR = 6;

    public static final int SOFT_CLOSE = 7;

    public static final int REGISTER_CLOSE = 8;

    public static final int REGISTER_SUCCESS = 9;

    public static final int REGISTER_ERROR = 10;

    public static final int VC_EMPTY = 11;

    public static final int VC_MISMATCHES = 12;

    public static final int NIMIETY = 13;

    public static final int SERVER_ERROR = 14;

}
