package com.xly.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author DELL
 * @date 2018/5/28
 */
public class DateUtils {

  /**
   * String 转 date （自定义格式）
   *
   * @param dateStr 时间字符串
   * @param format 时间模式
   * @return Date对象
   */
  public static Date stringToDate(String dateStr, String format) {

    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Date date = null;
    try {
      date = sdf.parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  /**
   * 判断日期是周几
   * @param date 要判断的日期
   * @return 0-7 的数字 分别代表 星期一至星期天
   */
  public static int getWeekDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    if (weekday == 0) {
      weekday = 7;
    }
    return weekday;
  }

  public static void main(String[] args) {
    Date date = new Date();
    getWeekDay(date);
  }

}
