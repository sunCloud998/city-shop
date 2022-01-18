package com.yidian.shop.utils;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * date 类型相关工具类
 *
 * @author zhoukun
 * @version 1.0
 * @date 2019-09-24
 */
@Slf4j
public class DateUtil {

    /**
     * 默认的日期格式组合，用来将字符串转化为日期用
     */
    private static final String[] DATE_PARSE_PATTERNS = {"yyyy/MM/dd",
            "yyyy-MM-dd", "yyyy年MM月dd日"};

    /**
     * 默认的时间格式
     */
    private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    /**
     * 默认的日期格式
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    public static final String EN_GB_DATE_PATTERN = "dd/MM/yyyy";

    public static final String ZH_CN_DATE_PATTERN = "yyyy-MM-dd";

    public static final String ZH_CN_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String[] ZH_CN_DATETIME_PATTERN_ARRAY = {"yyyy-MM-dd HH:mm:ss"};

    public static final String ZH_CN_DTP_NO_HOR = "yyyyMMdd HH:mm:ss";

    public static final String ZH_CN_DP_NO_HOR = "yyyyMMdd";

    public static final String ZH_CN_DP_NO_TRIM = "yyyyMMddHHmmss";

    /**
     * 日期代码，周日
     */
    public static final int SUNDAY = 1;

    /**
     * 日期代码，周一
     */
    public static final int MONDAY = 2;

    /**
     * 日期代码，周二
     */
    public static final int TUESDAY = 3;

    /**
     * 日期代码，周三
     */
    public static final int WEDNESDAY = 4;

    /**
     * 日期代码，周四
     */
    public static final int THURSDAY = 5;

    /**
     * 日期代码，周五
     */
    public static final int FRIDAY = 6;

    /**
     * 日期代码，周六
     */
    public static final int SATURDAY = 7;

    /**
     * 日期精度，秒
     */
    public static final int ACCURACY_SECOND = 1;

    /**
     * 日期精度，分
     */
    public static final int ACCURACY_MINUTE = 2;

    /**
     * 日期精度，小时
     */
    public static final int ACCURACY_HOUR = 3;

    /**
     * 日期精度，天
     */
    public static final int ACCURACY_DAY = 4;

    /**
     * 日期精度，月
     */
    public static final int ACCURACY_MONTH = 5;

    /**
     * 日期精度，年
     */
    public static final int ACCURACY_YEAR = 6;

    /**
     * 比较用日期格式，精度为年
     */
    private static final String ACCURACY_PATTERN_YEAR = "yyyy";

    /**
     * 比较用日期格式，精度为月
     */
    private static final String ACCURACY_PATTERN_MONTH = "yyyyMM";

    /**
     * 比较用日期格式，精度为日
     */
    private static final String ACCURACY_PATTERN_DAY = "yyyyMMdd";

    /**
     * 比较用日期格式，精度为时
     */
    private static final String ACCURACY_PATTERN_HOUR = "yyyyMMddHH";

    /**
     * 比较用日期格式，精度为分
     */
    private static final String ACCURACY_PATTERN_MINUTE = "yyyyMMddHHmm";

    /**
     * 比较用日期格式，精度为秒
     */
    private static final String ACCURACY_PATTERN_SECOND = "yyyyMMddHHmmss";

    /**
     * 单一属性格式，时
     */
    private static final String SINGLE_YEAR = "yyyy";

    /**
     * 单一属性格式，时
     */
    private static final String SINGLE_MONTH = "M";

    /**
     * 单一属性格式，时
     */
    private static final String SINGLE_DAY = "d";

    /**
     * 单一属性格式，时
     */
    private static final String SINGLE_HOUR = "H";

    /**
     * 单一属性格式，分
     */
    private static final String SINGLE_MINUTE = "m";

    /**
     * 单一属性格式，秒
     */
    private static final String SINGLE_SECOND = "s";

    /**
     *
     */
    private static final long MILLISECONDS_PER_SECOND = 1000;

    /**
     *
     */
    private static final long MILLISECONDS_PER_MINUTE = (long) 1000 * 60;

    /**
     *
     */
    private static final long MILLISECONDS_PER_HOUR = (long) 1000 * 60 * 60;

    /**
     *
     */
    private static final long MILLISECONDS_PER_DAY = (long) 1000 * 60 * 60 * 24;

    /**
     * 将给定的日期字符串，按照预定的日期格式，转化为Date型数据
     *
     * @param dateStr 日期字符字符串
     * @return 日期型结果
     */
    public static Date parseDate(String dateStr) {
        Date date = new Date();
        try {
            date = DateUtils.parseDate(dateStr,
                    DATE_PARSE_PATTERNS);
        } catch (ParseException e) {
            log.error("error", e);
        }
        return date;
    }

    public static Date parseLocalDateTime(String dateStr) {
        Date date = null;
        try {
            date = DateUtils.parseDate(dateStr, ZH_CN_DATETIME_PATTERN_ARRAY);
        } catch (ParseException e) {
            log.error("error", e);
        }
        return date;
    }

    public static Date getDate(String dateStr) {
        Date date = null;
        try {
            if (dateStr.length() == 8) {
                date = DateUtils.parseDate(
                        dateStr, new String[]{"yyyyMMdd"});
            } else {
                date = DateUtils.parseDate(
                        dateStr, new String[]{"yyyyMMddHHmmss", "yyyy-MM-dd",
                                "yyyy-MM-dd HH:mm:ss", "yyyyMMdd HH:mm:ss",
                                "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss"});
            }
        } catch (ParseException e) {
            log.error("error", e);
        }
        return date;
    }

    /**
     * 根据指定格式转化String型日期到Date型
     *
     * @param dateStr      String型日期
     * @param parsePattern 指定的格式
     * @return Date型日期
     */
    public static Date parseDate(String dateStr, String parsePattern) {
        Date date = null;
        try {
            date = DateUtils.parseDate(dateStr,
                    new String[]{parsePattern.toString()});
        } catch (ParseException e) {
            log.error("error", e);
        }
        return date;
    }

    /**
     * 返回系统当前时间（Date型）
     *
     * @return 系统当前时间
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 日期计算，日加减
     *
     * @param date   初始日期
     * @param amount 天数增量（负数为减）
     * @return 计算后的日期
     */
    public static Date addDays(Date date, int amount) {
        return DateUtils.addDays(date, amount);
    }

    /**
     * 日期计算，周加减
     *
     * @param date   初始日期
     * @param amount 周数增量（负数为减）
     * @return 计算后的日期
     */
    public static Date addWeeks(Date date, int amount) {
        return DateUtils.addWeeks(date, amount);
    }

    /**
     * 日期计算，月加减
     *
     * @param date   初始日期
     * @param amount 月数增量（负数为减）
     * @return 计算后的日期
     */
    public static Date addMonths(Date date, int amount) {
        return DateUtils.addMonths(date, amount);
    }

    /**
     * 日期计算，年加减
     *
     * @param date   初始日期
     * @param amount 年数增量（负数为减）
     * @return 计算后的日期
     */
    public static Date addYears(Date date, int amount) {
        return DateUtils.addYears(date, amount);
    }

    /**
     * 日期计算，小时加减
     *
     * @param date   初始日期
     * @param amount 小时增量（负数为减）
     * @return 计算后的日期
     */
    public static Date addHours(Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    /**
     * 日期计算，分钟加减
     *
     * @param date   初始日期
     * @param amount 分钟增量（负数为减）
     * @return 计算后的日期
     */
    public static Date addMinutes(Date date, int amount) {
        return DateUtils.addMinutes(date, amount);
    }

    /**
     * 日期计算，秒加减
     *
     * @param date   初始日期
     * @param amount 秒增量（负数为减）
     * @return 计算后的日期
     */
    public static Date addSeconds(Date date, int amount) {
        return DateUtils.addSeconds(date, amount);
    }

    /**
     * 根据指定格式，返回日期时间字符串
     *
     * @param date    日期变量
     * @param pattern 日期格式
     * @return 日期时间字符串
     */
    public static String getDateStr(Date date, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }


    /**
     * 根据Date型的日期，取Calendar型的日期
     *
     * @param date Date型的日期
     * @return Calendar型的日期
     */
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }


    /**
     * 根据指定规则，转化日期，如只取年、取年月等
     *
     * @param date    待转化日期
     * @param pattern 日期格式
     * @return 转化后的日期
     */
    public static Date transDateFormat(Date date, String pattern) {
        String dateStr = getDateStr(date, pattern);
        return parseDate(dateStr, pattern);
    }

    /**
     * 内部方法，计算时间点的差距
     *
     * @param startDate 起始时间
     * @param endDate   终止时间
     * @param unit      时间单位
     * @return 时间差
     */
    public static int getDistanceByUnit(Date startDate, Date endDate, int unit) {
        int result = 0;
        long millisecondPerUnit = MILLISECONDS_PER_DAY;
        switch (unit) {
            case ACCURACY_HOUR:
                millisecondPerUnit = MILLISECONDS_PER_HOUR;
                break;
            case ACCURACY_MINUTE:
                millisecondPerUnit = MILLISECONDS_PER_MINUTE;
                break;
            case ACCURACY_SECOND:
                millisecondPerUnit = MILLISECONDS_PER_SECOND;
                break;
            default:
                break;
        }
        long start = startDate.getTime();
        long end = endDate.getTime();
        long distance = end - start;
        result = Integer.valueOf((distance / millisecondPerUnit) + "");
        return result;
    }

    /**
     * 返回指定日期是当年的第几周
     *
     * @param date 指定日期
     * @return 周数（从1开始）
     */
    public static int getWeekOfYear(Date date) {
        return getCalendar(date).get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取指定日期是星期几
     *
     * @param date 指定日期
     * @return 星期日--1; 星期一--2; 星期二--3; 星期三--4; 星期四--5; 星期五--6; 星期六--7;
     */
    public static int getWeekOfDate(Date date) {
        return getCalendar(date).get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 判断指定年份日期的年份是否为闰年
     *
     * @param date 日期
     * @return 闰年ture，非闰年false
     */
    public static boolean isLeapYear(Date date) {
        int year = getCalendar(date).get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * 判断指定年份日期的年份是否为闰年
     *
     * @param year 年份数字
     * @return 闰年ture，非闰年false
     */
    public static boolean isLeapYear(int year) {
        if ((year % 400) == 0) {
            return true;
        } else if ((year % 4) == 0) {
            if ((year % 100) == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    /**
     * Format a datetime into a specific pattern.
     */
    public static String formatDate(Date date, String pattern) {
        if (!StringUtils.hasText(pattern)) {
            pattern = ZH_CN_DATETIME_PATTERN;
        }
        DateFormat formatter = createFormatter(pattern);
        return formatter.format(date);
    }

    public static DateFormat createFormatter(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * Format a datetime into a specific pattern.
     */
    public static String formatDate(Date date, String pattern, String timeZone) {
        if (!StringUtils.hasText(pattern)) {
            pattern = ZH_CN_DATETIME_PATTERN;
        }
        if (!StringUtils.hasText(timeZone)) {
            timeZone = "GMT+8";
        }
        DateFormat formatter = createFormatter(pattern, timeZone);
        return formatter.format(date);
    }

    public static DateFormat createFormatter(String pattern, String timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleDateFormat;
    }

    public static DateFormat createDateFormatter(Locale locale) {
        return DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
    }

    public static String formatDateTime(Object value, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        if (value == null) {
            return "";
        } else if (value instanceof String) {
            Date date = new Date();
            try {
                date = dateFormat.parse(value.toString());
            } catch (ParseException e) {
                return "";
            }
            return dateFormat.format(date);
        } else if (value instanceof Date) {
            return dateFormat.format(value);
        } else {
            return "";
        }
    }

    /**
     * 天数变更
     *
     * @param date
     * @param amount
     * @return Date
     */
    public static Date adjustDay(Date date, int amount) {
        Assert.notNull(date, "日期不能为空");
        Calendar calendar = dateToCalendar(date);
        adjustCalendar(calendar, Calendar.DAY_OF_MONTH, amount);
        return calendarToDate(calendar);
    }

    /**
     * 月份变更
     *
     * @param date
     * @param amount
     * @return Date
     */
    public static Date adjustMonth(Date date, int amount) {
        Assert.notNull(date, "日期不能为空");
        Calendar calendar = dateToCalendar(date);
        adjustCalendar(calendar, Calendar.MONTH, amount);
        return calendarToDate(calendar);
    }

    /**
     * 年度变更
     *
     * @param date
     * @param amount
     * @return Date
     */
    public static Date adjustYear(Date date, int amount) {
        Assert.notNull(date, "日期不能为空");
        Calendar calendar = dateToCalendar(date);
        adjustCalendar(calendar, Calendar.YEAR, amount);
        return calendarToDate(calendar);
    }

    public static Calendar dateToCalendar(Date date) {
        Assert.notNull(date, "日期不能为空");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }

    public static void adjustCalendar(Calendar calendar, int field, int amount) {
        calendar.add(field, amount);
    }

    public static Date getDateWithoutTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static Date getDateWithoutTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    /**
     * 根据配置调整时间 1d-1天, 1m-一月, 1y-一年
     *
     * @param date
     * @param scope
     * @return
     */
    public static final Date adjustDate(Date date, String scope) {
        scope = scope.trim();
        String mount = scope.substring(0, scope.length() - 1);
        char flag = scope.toUpperCase().toCharArray()[scope.length() - 1];
        Date resultDate = null;
        switch (flag) {
            case 'D':
                resultDate = DateUtils.addDays(date, Integer.parseInt(mount));
                break;
            case 'M':
                resultDate = DateUtils.addMonths(date, Integer.parseInt(mount));
                break;
            case 'Y':
                resultDate = DateUtils.addYears(date, Integer.parseInt(mount));
                break;
            default:
                throw new RuntimeException("日期标识符有误：" + flag);
        }
        return resultDate;
    }

    /**
     * 将java.util.Date 转换为java8 的java.time.LocalDateTime,默认时区为东8区
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateConvertToLocalDateTime(Date date) {
        return date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
    }

    /**
     * 将java8 的 java.time.LocalDateTime 转换为 java.util.Date，默认时区为东8区
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeConvertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
    }

    /**
     * "yyyy-MM-dd HH:mm:ss"格式的字符串转为LocalDateTime
     *
     * @param dateStr
     * @return
     */
    public static LocalDateTime stringParseLocalDateTime(String dateStr) {
        if (StringUtil.isBlank(dateStr)) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateStr, df);
    }

    /**
     * LocalDateTime转为"yyyy-MM-dd HH:mm:ss"格式的字符串
     *
     * @param time
     * @return
     */
    public static String stringParseLocalDateTime(LocalDateTime time) {
        if (ObjectUtils.isEmpty(time)) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return df.format(time);
    }

    public static String stringParseLocalDateTime(LocalDateTime time, String pattern) {
        if (ObjectUtils.isEmpty(time)) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(time);
    }

    public static String stringParseLocalDate(LocalDateTime time) {
        if (ObjectUtils.isEmpty(time)) {
            return null;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return df.format(time);
    }

    /**
     * 获取当前时间的LocalDatetime
     */
    public static LocalDateTime getNowLocalDateTime() {
        return LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai")));
    }

    /**
     * 获取当前时间的LocalDatetime
     */
    public static LocalDate getNowLocalDate() {
        return LocalDate.now(Clock.system(ZoneId.of("Asia/Shanghai")));
    }

    public static long getTicksByTimestamp() {
        long ticksAtEpoch = 621355968000000000L;
        long ticksPerMillisecond = 10000;
        long time = System.currentTimeMillis();
        return (time + 28800000) * ticksPerMillisecond + ticksAtEpoch;
    }


    /**
     * 现在到明天0点的秒数.
     *
     * @return
     */
    public static Long toTomorrowSecond() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = cal.getTime();
        Date now = new Date();
        long timeDelta = (tomorrow.getTime() - now.getTime()) / 1000;//单位是秒  现在到明天0点的秒数
        return timeDelta;
    }


    /**
     * 判断时间是不是今天
     *
     * @param date
     * @return 是返回true，不是返回false
     */
    public static Boolean dateIsToday(Date date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);

        return day.equals(nowDay);

    }

    /**
     * string:yyyy-MM-dd,转 LocalDate
     *
     * @param time
     * @return
     */
    public static LocalDate localDateParseString(String time) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(time, fmt);
    }

}
