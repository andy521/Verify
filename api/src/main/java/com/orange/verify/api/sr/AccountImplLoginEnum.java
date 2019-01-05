package com.orange.verify.api.sr;

import java.io.Serializable;

public class AccountImplLoginEnum implements Serializable {

    public static final int LOGIN_SUCCESS = 1;

    public static final int LOGIN_ERROR = 2;

    public static final int KEY_EMPTY = 3;

    public static final int SOFT_EMPTY = 4;

    public static final int SOFT_CLOSE = 5;

    public static final int KEY_ERROR = 6;

    public static final int PASSWORD_LENGTH_ERROR = 7;

    public static final int ACCOUNT_NOT_BOUND_CARD = 8;

    public static final int CARD_CLOSURE = 9;

    public static final int CARD_PAST_DUE = 10;

    public static final int ACCOUNT_BLACKLIST = 11;

    public static final int ACCOUNT_EMPTY = 12;

    public static final int BAIDU_API_ERROR = 13;

    public static final int PASSWORD_ERROR = 14;

}
