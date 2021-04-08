package com.company;

import com.company.domain.DateParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //出生日期
        Date t1 = dateFormat.parse("2020-09-07 10:10:00");
        //当前日期
        Date t2 = dateFormat.parse("2021-04-07 10:01:00");

        DateParams reuquest = new DateParams();
        reuquest.setBirthDate(t1);
        reuquest.setNowDate(t2);

        String s1 = DateUtils.timestampDifference(reuquest);
        System.out.println(s1);
    }
}
