package com.orange.verify.api.sr;

import java.io.Serializable;

public class AccountImplBindingCodeEnum implements Serializable {

    public static final int BINDING_CODE_SUCCESS = 1;

    public static final int KEY_EMPTY = 2;

    public static final int SOFT_EMPTY = 3;

    public static final int KEY_ERROR = 4;

    public static final int PASSWORD_LENGTH_ERROR = 5;

    public static final int SOFT_CLOSE = 6;

    public static final int ACCOUNT_EMPTY = 7;

    public static final int SOFT_NO_CHANGE = 8;

    public static final int ACCOUNT_BLACKLIST = 9;

    public static final int PASSWORD_ERROR = 10;

    public static final int BINDING_CODE_ERROR = 11;

}
