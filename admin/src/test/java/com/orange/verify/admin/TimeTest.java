package com.orange.verify.admin;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class TimeTest {

    public static void main(String[] args) {

        String now = DateUtil.now();

        System.out.println(now);

        Date date = DateUtil.parse(now);

        long time1 = date.getTime();
        System.out.println(time1);

        DateTime dateTime = DateUtil.offsetMillisecond(date, 1);
        long time = dateTime.getTime();

        System.out.println(time - System.currentTimeMillis());

        System.out.println(time);
        String toDateStr = dateTime.toString();
        System.out.println(toDateStr);

    }

}
