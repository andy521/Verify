package com.orange.verify.api.sr;

import java.io.Serializable;

public class AccountImplUpdatePasswordEnum implements Serializable {

    public static final int UPDATE_PASSWORD_SUCCESS = 1;

    public static final int UPDATE_PASSWORD_ERROR = 2;

    public static final int SOFT_EMPTY = 3;

    public static final int SOFT_CLOSE = 4;

    public static final int ACCOUNT_EMPTY = 5;

    public static final int ACCOUNT_BLACKLIST = 6;

    public static final int SECURITY_CODE_ERROR = 7;

}
