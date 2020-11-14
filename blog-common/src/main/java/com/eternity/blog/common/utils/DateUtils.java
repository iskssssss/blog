package com.eternity.blog.common.utils;

import com.eternity.blog.common.enums.DateTimeFormat;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * @Description 时间工具类
 * @Author eternity
 * @Date 2020/4/12 23:48
 */
public class DateUtils {

    /**
     * 获取当前时间
     *
     * @return 当前时间（毫秒）
     */
    private static LocalDateTime getNow() {
        return LocalDateTime.now();

    }

    /**
     * 将 String 类型的时间格式转为 DateTimeFormatter
     *
     * @param format 格式
     * @return DateTimeFormatter
     */
    private static DateTimeFormatter toDataTimeFormat(String format) {
        return DateTimeFormatter.ofPattern(format);
    }

    /**
     * 获取当前时间
     *
     * @param format 指定格式
     * @return 时间
     */
    public static String getDateTime(String format) {
        return getNow().format(toDataTimeFormat(format));
    }

    /**
     * 获取当前时间(yyyy-MM-dd HH:mm:ss)
     *
     * @return 当前时间
     */
    public static String getDateTime() {
        return getDateTime(DateTimeFormat.DATE_TIME_FORMAT.getFormat());
    }

    /**
     * 获取当前日期(yyyy-MM-dd)
     *
     * @return 当前时间
     */
    public static String getDate() {
        return getDateTime(DateTimeFormat.DATE_FORMAT.getFormat());
    }

    /**
     * 获取当前时间(HH:mm:ss)
     *
     * @return 当前时间
     */
    public static String getTime() {
        return getDateTime(DateTimeFormat.TIME_FORMAT.getFormat());
    }

    /**
     * 将字符串时间转换为 LocalDateTime (yyyy-MM-dd HH:mm:ss)
     *
     * @return 当前时间
     */
    public static LocalDateTime toLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormat.DATE_TIME_FORMAT.getDateTimeFormat());
    }

    /**
     * 将字符串时间转换为 LocalDate (yyyy-MM-dd)
     *
     * @return 当前时间
     */
    public static LocalDate toLocalDate(String date) {
        return toLocalDateTime(date).toLocalDate();
    }

    /**
     * 将字符串时间转换为 LocalTime (HH:mm:ss)
     *
     * @return 当前时间
     */
    public static LocalTime toLocalTime(String time) {
        return toLocalDateTime(time).toLocalTime();
    }

    /**
     * 当前时间减去一个特定的时间
     *
     * @param amountToSubtract 时间
     * @param unit             类型
     * @return LocalDateTime
     */
    public static LocalDateTime minus(long amountToSubtract, TemporalUnit unit) {
        return getNow().minus(amountToSubtract, unit);
    }

    /**
     * 将 LocalDateTime 转为字符串
     *
     * @param date 日期
     * @return String
     */
    public static String toString(LocalDateTime date) {
        return DateTimeFormat.DATE_TIME_FORMAT.getDateTimeFormat().format(date);
    }

    /**
     * 将字符串转为Date
     *
     * @return Date
     */
    public static Date toDate(String dateTime) {
        return Date.from(toLocalDateTime(dateTime).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将Date转为字符串
     *
     * @param date 日期
     * @return String
     */
    public static String toString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DateTimeFormat.DATE_TIME_FORMAT.getFormat());
        return format.format(date);
    }

    public static String getDate(String dateTime){
        return dateTime.split(" ")[0];
    }
}
