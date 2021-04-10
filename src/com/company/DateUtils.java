package com.company;

import com.company.domain.AgeNode;
import com.company.domain.DateParams;
import com.company.domain.TimestampDto;

import java.time.*;
import java.util.Date;

/**
 * @author lijiaxing
 */
public class DateUtils {
    public static String timestampDifference (DateParams request) {
        Date fromDate = request.getBirthDate();
        Date toDate = request.getNowDate();

        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime fromDateTime = LocalDateTime.ofInstant(fromDate.toInstant(), zoneId);
        LocalDateTime toDateTime = LocalDateTime.ofInstant(toDate.toInstant(), zoneId);

        long toMillis = Duration.between(fromDateTime, toDateTime).toMillis();
        if (toMillis <= 0) {
            return "请检查时间参数是否正确！";
        }

        //将年月日时分秒拆分，赋值给做差用的时间对象
        TimestampDto fromTimestampDto =
                new TimestampDto(fromDateTime.getYear(), fromDateTime.getMonthValue(),
                        fromDateTime.getDayOfMonth(), fromDateTime.getHour(),
                        fromDateTime.getMinute(), fromDateTime.getSecond());

        TimestampDto toTimestampDto =
                new TimestampDto(toDateTime.getYear(), toDateTime.getMonthValue(),
                        toDateTime.getDayOfMonth(), toDateTime.getHour(),
                        toDateTime.getMinute(), toDateTime.getSecond());

        //直接做差，借位是得到相应年份，月份，天数，小时，分钟等数值，然后做加减
        TimestampDto resutlTimestampDto = timestampDtoSubtraction(fromTimestampDto, toTimestampDto);


        int years = resutlTimestampDto.getYears();
        int months = resutlTimestampDto.getMonths();
        int days = resutlTimestampDto.getDays();
        //患者最终年龄
        StringBuilder patientAge = new StringBuilder();
        //时间节点
        AgeNode ageNode = new AgeNode();

        /*
         * 年龄大于等于3*12个月的，用岁表示；
         * 小于3*12个月而又大于等于1*12个月的，用岁月表示；
         * 小于12个月而又大于等于6个月的，用月表示；
         * 小于6个月而又大于等于1个月的，用月天表示；
         * 小于1个月而又大于等于72小时的(3天)，用天表示
         * 小于72小时的，用小时表示。
         */
        if (years >= ageNode.getYearNodeEnd()) {
            //年龄大于等于3*12个月的，用岁表示；
            patientAge.append(years).append("岁");
        } else if ((years >= ageNode.getYearNodeBegin()) && (years < ageNode.getYearNodeEnd())) {
            //小于3*12个月而又大于等于1*12个月的，用岁月表示；
            patientAge.append(years).append("岁");
            if (months > 0) {
                patientAge.append(months).append("月");
            }
        } else if ((years < ageNode.getYearNodeBegin()) && (months >= ageNode.getMouthNodeEnd())) {
            //小于12个月而又大于等于6个月的，用月表示；
            patientAge.append(months).append("月");
        } else if ((months < ageNode.getMouthNodeEnd()) && (months >= ageNode.getMouthNodeBegin())) {
            //小于6个月而又大于等于1个月的，用月天表示；
            patientAge.append(months).append("月");
            if (days > 0) {
                patientAge.append(days).append("天");
            }
        } else if (months < ageNode.getMouthNodeBegin() && days >= ageNode.getDayNode()) {
            //小于1个月而又大于等于72小时的(3天)，用天表示
            patientAge.append(days).append("天");
        } else if (years == 0 && months == 0 && days < ageNode.getDayNode()) {
            long toHours = Duration.between(fromDateTime, toDateTime).toHours();
            //小于72小时的，用小时表示。
            if (toHours < 1) {
                patientAge.append(0).append("小时");
            } else {
                patientAge.append(toHours).append("小时");
            }
        }
        return patientAge.toString();
    }

    private static TimestampDto timestampDtoSubtraction (TimestampDto fromTimestampDto, TimestampDto toTimestampDto) {
        //做差的结果
        TimestampDto result = new TimestampDto(0,0,0,0,0,0);

        //用目标时间－出生时间
        int sencondsSub = toTimestampDto.getSeconds() - fromTimestampDto.getSeconds();
        int minutesSub = toTimestampDto.getMinutes() - fromTimestampDto.getMinutes();
        int hoursSub = toTimestampDto.getHours() - fromTimestampDto.getHours();
        int daysSub = toTimestampDto.getDays() - fromTimestampDto.getDays();
        int monthsSub = toTimestampDto.getMonths() - fromTimestampDto.getMonths();
        int yearsSub = toTimestampDto.getYears() - fromTimestampDto.getYears();

        result.setSeconds(sencondsSub);
        result.setMinutes(minutesSub);
        result.setHours(hoursSub);
        result.setDays(daysSub);
        result.setMonths(monthsSub);
        result.setYears(yearsSub);

        //年月日时分秒分别做差，不够减的借位，秒去分借，分去小时借，小时去天借，天去月借以此类推
        int secondsResult = result.getSeconds();
        //秒不够减，去分钟借位，分钟减1
        if (secondsResult < 0) {
            result.setMinutes(result.getMinutes() - 1);
            result.setSeconds(secondsResult + 60);
        }

        int minutesResult = result.getMinutes();
        if (minutesResult < 0) {
            result.setHours(result.getHours() - 1);
            result.setMinutes(minutesResult + 60);
        }

        //小时不够减，去天借位，借来24小时，天减一
        int hoursResult = result.getHours();
        if (hoursResult < 0) {
            result.setDays(result.getDays() - 1);
            result.setHours(hoursResult + 24);
        }

        //天不够减去月借位，借当前月份上个月的天数
        int daysResult = result.getDays();
        if (daysResult <0) {
            result.setMonths(result.getMonths() - 1);
            int daysOfMonth = getDaysOfMonth(fromTimestampDto.getYears(), fromTimestampDto.getMonths());
            result.setDays(daysResult + daysOfMonth);
        }

        //一共有12个月，减为0或者负数加12，为月份的真实值
        int monthsResult = result.getMonths();
        if (monthsResult < 0) {
            result.setYears(result.getYears() - 1);
            result.setMonths(monthsResult + 12);
        }

        return result;
    }

    //返回传入月份的天数
    private static int getDaysOfMonth (int year, int month) {
        int result = 0;
        switch (month) {
            case 0:
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                result = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                result =  30;
                break;
            case 2:
                result = isLeapYear(year) ? 29 : 28;
                break;
        }
        return result;
    }
    //判断传入年份是否为闰年
    private static boolean isLeapYear (int year) {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }
}
