package com.orange.verify.admin;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class TimeTest {

    public static void main(String[] args) {

        long a = 1545910903611L;

        long totalTime = (System.currentTimeMillis() - a);

        System.out.println(totalTime);

    }

}
