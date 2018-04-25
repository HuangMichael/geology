package com.yhb.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by huangbin on 2017/6/14.
 */
public class DateUtils {


    /**
     * @param dateString
     * @param format
     * @return 精确到年月的字符串dateString 转换为 格式为format的精确到年月日的时间类型，而且当字符串为空时设置默认值。
     * 如把2017-07转换为开始年份为2017-07-01，结束年份为2017-07-31
     */
    public static Date convertStr2DateWithDefault(String dateString, String format, boolean isBeginYear) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        if ((isBeginYear && dateString == null) || (isBeginYear && dateString.trim().isEmpty())) {
            try {
                date = simpleDateFormat.parse("0001-01-01");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (isBeginYear && dateString.trim().length() == 7) {
            dateString += "-01";
            try {
                date = simpleDateFormat.parse(dateString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ((!isBeginYear && dateString == null) || (!isBeginYear && dateString.trim().isEmpty())) {
            try {
                date = simpleDateFormat.parse("9999-12-31");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (!isBeginYear && dateString.trim().length() == 7) {
            try {
                int year = Integer.parseInt(dateString.substring(0, 4));
                int month = Integer.parseInt(dateString.substring(5));
                //以下代码为获取该年月的最后一天。比如输入的 dateString 为 "2017-07",那么它的最后一天就是"2017-07-31"
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);//设置年份的值
                cal.set(Calendar.MONTH, month); //设置月份下标index,一月份为0，二月份为1，以此类推。。。month设置为7则代表8月份。
                cal.set(Calendar.DAY_OF_MONTH, 1);//获取新月份的第一天，即2017-08-01
                cal.add(Calendar.DAY_OF_MONTH, -1);//再减去一天，即为原月份的最后一天2017-07-31
                cal.set(Calendar.HOUR_OF_DAY, 23);//设置时
                cal.set(Calendar.MINUTE, 59);//设置分
                cal.set(Calendar.SECOND, 59);//设置秒
                date = cal.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (dateString.trim().length() == 10) {
            try {
                date = simpleDateFormat.parse(dateString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * @param dateString
     * @param format
     * @return 精确到年月日的字符串dateString  转换为 格式为format的精确到时分秒的时间类型，而且当字符串为空时设置默认值。
     * 如把 2017-07-12 转换为开始时间为 2017-07-12 00:00:00 ，结束时间 为2017-07-12 23:59:59 。
     */
    public static Date convertStr2DateTimeWithDefault(String dateString, String format, boolean isBegin) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        if ((isBegin && dateString == null) || (isBegin && dateString.trim().isEmpty())) {
            try {
                date = simpleDateFormat.parse("0001-01-01 00:00:00");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (isBegin && dateString.trim().length() == 10) {
            try {
                int year = Integer.parseInt(dateString.substring(0, 4));
                int month = Integer.parseInt(dateString.substring(5, 7));
                int day = Integer.parseInt(dateString.substring(8));
                //以下代码为获取该年月的最后一天。比如输入的 dateString 为 "2017-07",那么它的最后一天就是"2017-07-31"
                Calendar cal = Calendar.getInstance();
                cal.set(year, month - 1, day, 0, 0, 0);//设置年月日时分秒的值
                date = cal.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ((!isBegin && dateString == null) || (!isBegin && dateString.trim().isEmpty())) {
            try {
                date = simpleDateFormat.parse("9999-12-31 23:59:59");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (!isBegin && dateString.trim().length() == 10) {
            try {
                int year = Integer.parseInt(dateString.substring(0, 4));
                int month = Integer.parseInt(dateString.substring(5, 7));
                int day = Integer.parseInt(dateString.substring(8));
                //以下代码为获取该年月的最后一天。比如输入的 dateString 为 "2017-07",那么它的最后一天就是"2017-07-31"
                Calendar cal = Calendar.getInstance();
                cal.set(year, month - 1, day, 23, 59, 59);//设置年月日时分秒的值
                date = cal.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * @param dateString
     * @param format
     * @return 日期字符串 转换为 格式为format的date类型
     */
    public static Date convertStr2Date(String dateString, String format) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * @param date
     * @param format
     * @return 字符串转集合 Long
     * @throws Exception
     */
    public static String convertDate2Str(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String dateStr = "";
        try {
            if (date == null) {
                date = new Date();
            }
            dateStr = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }


    /**
     * @param inputDateStr
     * @return 返回次日 00:00:00
     */
    public static Date getCellingDate(String inputDateStr) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date inputDate = null;

        try {
            inputDate = simpleDateFormat.parse(inputDateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date outDate = calendar.getTime();

        return outDate;
    }

    /**
     * @param inputDate
     * @return 返回次日 00:00:00
     */
    public static Date getCellingDate(Date inputDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date outDate = calendar.getTime();

        return outDate;
    }


    /**
     * @param inputDate
     * @return 返回次日 00:00:00
     */
    public static Date addDate(Date inputDate, int type, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        calendar.add(type, num);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date outDate = calendar.getTime();

        return outDate;
    }

    /**
     * @param beginDate
     * @param num
     * @param type
     * @return 返回日期list
     */
    public static Date addDateByNumAndType(Date beginDate, int num, int type) {
        int typeArray[] = {Calendar.DATE, Calendar.MONTH, Calendar.YEAR};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        calendar.add(typeArray[type], num);
        System.out.println("date------" + calendar.getTime());
        return calendar.getTime();
    }
}
