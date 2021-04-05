package com.company.domain;

import java.util.Date;

/**
 * @author lijiaxing
 * birthDate: 出生日期
 * nowDate: 当前日期
 */
public class DateParams {
    private Date birthDate;
    private Date nowDate;

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getNowDate() {
        return nowDate;
    }

    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }
}
