package com.shandian.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 
 * 神州鹰掌通家园项目组 Title: 时间工具类 Description: Copyright: Copyright (c) 2014 厦门神州鹰软件科技有限公司
 * 
 * 
 */
public class DateUtils {
    public static final String YEAR = "year";// 年份
    public static final String WEEKNUM = "weekNum";// 周数
    public static final String TYPE_DATE_BEFORE = "date_before";
    public static final String TYPE_DATE_AFTER = "date_after";

    /**
     * 格式化 timestamp
     * 
     * @author: 涂燕东
     */
    public static String formatTimestamp(Timestamp timestamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(timestamp);
    }

    public static String formatTimestamp(Date timestamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(timestamp);
    }

    /**
     * dayNumt天前的时间
     * 
     * @param monthNum 天数
     * @return yyyy-MM-dd
     */
    public static String getAgoDateTimeByDay(int dayNum) {
        Format f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, dayNum);
        return f.format(c.getTime());
    }

    public static String getDateFormatShort(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        if (null != currentTime) {
            String dateString = formatter.format(currentTime);
            return dateString;
        } else {
            return "";
        }
    }

    public static String strFormatDateShort(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String dest = formatter.format(DateUtils.strToDateLong(date));
        return dest;
    }

    /**
     * 
     * 方法描述：获取系统当前时间前几天时间
     * 
     * @return
     */
    public static Date getAgoDateByDay(int dayNum) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, dayNum);
        return cal.getTime();
    }

    public static Date formatStringToDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 获取现在时间
     * 
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(0);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 
     * 方法描述：获取当前时间
     * 
     * @author: 胡耀忠 hyz
     * @date： 日期：2014-5-13 时间：上午10:09:19
     * @return Timestamp
     * @version 1.0
     */
    public static Timestamp getNowTime() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * 格式时间
     * 
     * @param date
     * @param format
     * @return
     */
    public static Date getStringToDate(String date, String format) {
        try {
            if (date == null || "".equals(date)) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把形如GMT+0.8 GMT+8.0 GMT+8:00 GMT+08:00中国的标准时间改为yyyy-MM-dd HH:mm:ss的格式
     * 
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getDateStr(String date) {
        if (!"".equals(date)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dest = format.format(Date.parse(date));
            return dest;
        } else {
            return "";
        }
    }

    /**
     * 获取时间格式： HH:mm
     * 
     * @param date
     * @return
     */
    public static String getDateStrHM(String date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String dest = format.format(DateUtils.strToDateLong(date));
        return dest;
    }

    /**
     * 
     * 方法描述：获取当前时分
     * 
     * @author: 胡耀忠 hyz 日期：2014-8-20 时间：上午09:45:00
     * @version 1.0
     */
    public static String getNowDateStrHM() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String dateString = format.format(new Date());
        return dateString;
    }

    /**
     * 获取现在时间
     * 
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getDateLong(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return DateUtils.strToDateLong(dateString);
    }

    /**
     * 获取现在时间
     * 
     * @throws ParseException
     * 
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        try {
            Date currentTime = new Date();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(currentTime);
            Date currentTime_2 = formatter.parse(dateString);
            return currentTime_2;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将长格式字符串改为yyyy-MM-dd格式
     * 
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static String getDateShort(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (null != currentTime) {
            String dateString = formatter.format(currentTime);
            return dateString;
        } else {
            return "";
        }
    }

    public static String getDateShortChina(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        if (null != currentTime) {
            String dateString = formatter.format(currentTime);
            return dateString;
        } else {
            return "";
        }
    }

    public static String getHHMM(Timestamp time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH时mm分");
        String format = formatter.format(time);
        return format;
    }

    /**
     * 
     * 方法描述：返回月份和日期
     * 
     * @author: 胡耀忠 hyz 日期：2014-11-11 时间：上午10:42:57
     * @param time
     * @return
     * @version 1.0
     */
    public static String getDateToMMDDStr(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        if (null != time) {
            String dateString = formatter.format(time);
            return dateString;
        } else {
            return "";
        }
    }

    /**
     * 将长格式字符串改为yyyy-MM-dd hh:mm:ss格式
     * 
     * @return返回短时间格式yyyy-MM-dd hh:mm:ss
     */
    public static String getDateLongL(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     * 
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     * 
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     * 
     * @return
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getTimeShort(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getTimeShorts() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getTimeShorts(Date str) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(str);
        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     * 
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        if (strDate != null) {
            Date strtodate = formatter.parse(strDate, pos);
            return strtodate;
        }
        return null;
    }

    /**
     * 方法描述：将自定义的时间格式字符串转换为时间
     * 
     * @author: 苏志芳 suzf
     * @date： 日期：2015-6-24 时间：下午02:21:06
     * @param strDate
     * @param format
     * @return
     * @version 2.8
     */
    public static Date strToDate(String strDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        if (strDate != null) {
            Date strtodate = formatter.parse(strDate, pos);
            return strtodate;
        }
        return null;
    }

    public static Date strToDateShort(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        if (strDate != null) {
            Date strtodate = formatter.parse(strDate, pos);
            return strtodate;
        }
        return null;
    }

    /**
     * 将时间mm/dd/yyyy格式转换为yyyy-mm-dd
     * 
     * @param date 时间字符串
     * @return
     */
    public static String dateToStr(String date) {
        String[] strDate = date.split("/");
        String dateString = strDate[2] + "-" + strDate[1] + "-" + strDate[0];
        return dateString;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     * 
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (dateDate != null) {
            String dateString = formatter.format(dateDate);
            return dateString;
        } else {
            return "";
        }
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     * 
     * @param dateDate
     * @param k
     * @return
     */
    public static String dateToStr(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (dateDate != null) {
            String dateString = formatter.format(dateDate);
            return dateString;
        } else {
            return "";
        }
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     * 
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        if (strDate == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy
     * 
     * @param strDate
     * @return
     */
    public static Date strToDateYYYY(String strDate) {
        if (strDate == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 得到现在时间
     * 
     * @return
     */
    public static Date getNow() {
        Date currentTime = new Date();
        return currentTime;
    }

    /**
     * 提取一个月中的最后一天
     * 
     * @param day
     * @return
     */
    public static Date getLastDate(long day) {
        Date date = new Date();
        long date_3_hm = date.getTime() - 3600000 * 34 * day;
        Date date_3_hm_date = new Date(date_3_hm);
        return date_3_hm_date;
    }

    /**
     * 得到现在时间
     * 
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 
     * 方法描述：获取距离今天i天的日期
     * 
     * @author: 陈辉鑫
     * @date： 日期：2015-5-14 时间：下午03:55:20
     * @param i
     * @return
     * @version 2.9
     */
    public static String getDay(int i) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, i);
        String day = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return day;
    }

    /**
     * 
     * 方法描述：返回当前时间
     * 
     * @author: 陈辉鑫
     * @date： 日期：2015-5-14 时间：下午03:55:20
     * @param i
     * @return
     * @version 2.9
     */
    public static String getCurrTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        return date;
    }

    /**
     * 得到现在时间
     * 
     * @return 学符串yyyyMMdd
     */
    public static String getStringShortToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 得到现在小时
     */
    public static String getHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        return formatter.format(currentTime);
    }

    /**
     * 得到现在分钟
     * 
     * @return
     */
    public static String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("mm");
        return formatter.format(currentTime);
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     * 
     * @param sformat yyyyMMddhhmmss
     * @return
     */
    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
     */
    public static String getTwoHour(String st1, String st2) {
        String[] kk = null;
        String[] jj = null;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
            return "0";
        else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            java.util.Date date = myFormatter.parse(sj1);
            java.util.Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public static String getPreTime(String sj1, String jj) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydate1 = "";
        try {
            Date date1 = format.parse(sj1);
            long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (Exception e) {}
        return mydate1;
    }

    /**
     * 
     * 方法描述：时间前推或后推几秒
     * 
     * @author: 胡耀忠 hyz 日期：2014-10-10 时间：上午09:36:56
     * @param seconds
     * @return
     * @version 1.0
     */
    public static Date getPreTime(int seconds) {
        Date d = new Date();
        long Time = d.getTime() + seconds * 1000;
        d.setTime(Time);
        return d;
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = strToDate(nowdate);
            long myTime = (long) ((d.getTime() / 1000) + Float.parseFloat(delay) * 24 * 60 * 60);
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数 转换为yyyy-MM-dd HH:mm:ss
     */
    public static String getNextDayByNowDate(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String mdate = "";
            Date d = strToDate(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 
     * 方法描述：得到一个时间延后或前移几天的时间,date为时间,delay为前移或后延的天数
     * 
     * @author: 胡耀忠 hyz
     * @date： 日期：2014-4-18 时间：上午10:50:11
     * @param date
     * @param delay
     * @return
     * @version 1.0
     */
    public static Date getNextDate(Date date, int delay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, delay);
        return cal.getTime();
    }

    /**
     * 判断是否润年
     * 
     * @param ddate
     * @return
     */
    public static boolean isLeapYear(String ddate) {
        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = strToDate(ddate);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        // return (year%400==0)||(year%400!=0&year%4==0&&year%100!=0);
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
     * 返回美国时间格式 26 Apr 2006
     * 
     * @param str
     * @return
     */
    public static String getEDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
    }

    /**
     * 获取一个月的最后一天
     * 
     * @param dat
     * @return
     */
    public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
        String str = dat.substring(0, 8);
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            str += "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            str += "30";
        } else {
            if (isLeapYear(dat)) {
                str += "29";
            } else {
                str += "28";
            }
        }
        return str;
    }

    /**
     * 判断二个时间是否在同一个周
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) return true;
        }
        return false;
    }

    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     * 
     * @return
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1) week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

    /**
     * 获取日期周序列,即得到时间所在的年度是第几周
     * 
     * @return
     */
    public static String getSeqWeek(Date date) {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(date);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1) week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

    /**
     * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
     * 
     * @param sdate
     * @param num
     * @return
     */
    public static String getWeek(String sdate, String num) {
        // 再转换为时间
        Date dd = DateUtils.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (num.equals("1")) // 返回星期一所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        else if (num.equals("2")) // 返回星期二所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        else if (num.equals("3")) // 返回星期三所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        else if (num.equals("4")) // 返回星期四所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        else if (num.equals("5")) // 返回星期五所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        else if (num.equals("6")) // 返回星期六所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        else if (num.equals("0")) // 返回星期日所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     * 
     * @param sdate
     * @return
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = DateUtils.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     * 
     * @param sdate
     * @return
     */
    public static String getWeekCn(String sdate) {
        // 再转换为时间
        Date date = DateUtils.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE", Locale.CHINA).format(c.getTime());
    }

    public static String getWeekStr(String sdate) {
        String str = "";
        str = DateUtils.getWeek(sdate);
        if ("星期日".equals(str)) {
            str = "周日";
        } else if ("星期一".equals(str)) {
            str = "周一";
        } else if ("星期二".equals(str)) {
            str = "周二";
        } else if ("星期三".equals(str)) {
            str = "周三";
        } else if ("星期四".equals(str)) {
            str = "周四";
        } else if ("星期五".equals(str)) {
            str = "周五";
        } else if ("星期六".equals(str)) {
            str = "周六";
        }
        // System.out.println(str);
        return str;
    }

    /**
     * 两个时间之间的天数
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals("")) return 0;
        if (date2 == null || date2.equals("")) return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        java.util.Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {}
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 两个时间之间的天数
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(Date end, Date start) {
        if (end == null) return 0;
        if (start == null) return 0;
        // 转换为标准时间
        end = org.apache.commons.lang.time.DateUtils.truncate(end, Calendar.DATE);
        start = org.apache.commons.lang.time.DateUtils.truncate(start, Calendar.DATE);
        long day = (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间 此函数返回该日历第一行星期日所在的日期
     * 
     * @param sdate
     * @return
     */
    public static String getNowMonth(String sdate) {
        // 取该时间所在月的一号
        sdate = sdate.substring(0, 8) + "01";
        // 得到这个月的1号是星期几
        Date date = DateUtils.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int u = c.get(Calendar.DAY_OF_WEEK);
        String newday = DateUtils.getNextDay(sdate, (1 - u) + "");
        return newday;
    }

    /**
     * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
     * 
     * @param k 表示是取几位随机数，可以自己定
     */
    public static String getNo(int k) {
        return getUserDate("yyyyMMddHHmmss") + getRandom(k);
    }

    /**
     * 返回一个随机数
     * 
     * @param i
     * @return
     */
    public static String getRandom(int i) {
        Random jjj = new Random();
        if (i == 0) return "";
        String jj = "";
        for (int k = 0; k < i; k++) {
            jj = jj + jjj.nextInt(9);
        }
        return jj;
    }

    /**
     * 
     * @param args
     */
    public static boolean RightDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (date == null) {
            return false;
        }
        if (date.length() > 10) {
            sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        try {
            sdf.parse(date);
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * 将formBean 里的字符时间(yyyy-MM-dd) 转换成Date类型
     * 
     * @param formDate
     * @return
     */
    public static Date formBeanDateToPODate(String formDate) {
        try {
            if (formDate != null) {
                if (!formDate.trim().equals("")) {
                    System.out.println("---------formdate:" + formDate);
                    return java.sql.Date.valueOf(formDate);
                }
            }
        } catch (Exception e) {
            System.out.println("DateUtils:时间转换异常");
            return new Date();
        }
        return null;
    }

    // 获取年月的方法
    public static String getYearAndMonth() {
        SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy年M月");
        return dateformat2.format(new Date());
    }

    // 获取年月日的方法
    public static String getYearMonthDay() {
        SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy年MM月dd日");
        return dateformat2.format(new Date());
    }

    // 获取月日的方法
    public static String getMonthDay(String datestr) {
        Date date = DateUtils.strToDate(datestr);
        SimpleDateFormat dateformat2 = new SimpleDateFormat("M月d日");
        return dateformat2.format(date);
    }

    // 获取月日的方法
    public static String getDay(String datestr) {
        Date date = DateUtils.strToDate(datestr);
        SimpleDateFormat dateformat2 = new SimpleDateFormat("dd");
        return dateformat2.format(date);
    }

    // 获取第几周
    public static String getDayMonth(String sdate) {
        // 再转换为时间
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.WEEK_OF_MONTH);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return hour + "";
    }

    /**
     * 得到当天是本月第几周星期几
     * 
     * @return
     */
    public static String getMonthWeekInToday() {
        String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);// 获致是本周的第几天地, 1代表星期天...7代表星期六
        return weeks[day - 1];
    }

    /**
     * 得到传过来的日期是本月第几周星期几
     * 
     * @return
     */
    public static String getMonthWeekInToday(String date) {
        String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar c = Calendar.getInstance();
        c.setTime(strToDate(date));
        int day = c.get(Calendar.DAY_OF_WEEK);// 获致是本周的第几天地, 1代表星期天...7代表星期六
        return weeks[day - 1];
    }

    public static String getDayWeek(String sdate, String strs) {
        // 再转换为时间
        Date date = DateUtils.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String str = "";
        int hour = c.get(Calendar.DAY_OF_WEEK);
        if (hour == 1) {
            str = strs + "日";
        } else if (hour == 2) {
            str = strs + "一";
        } else if (hour == 3) {
            str = strs + "二";
        } else if (hour == 4) {
            str = strs + "三";
        } else if (hour == 5) {
            str = strs + "四";
        } else if (hour == 6) {
            str = strs + "五";
        } else if (hour == 7) {
            str = strs + "六";
        }
        return str;
    }

    private static int days; // 天数
    private static int hours; // 时
    private static int minutes; // 分
    private static int seconds; // 秒

    public static String getQuot(String time1, String time2) {
        long quot = 0;
        String str = "";
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date1 = ft.parse(time1);
            Date date2 = ft.parse(time2);
            quot = date1.getTime() - date2.getTime();
            int totalSeconds = (int) (quot / 1000);
            // 得到总天数
            days = totalSeconds / (3600 * 24);
            if (days > 0) {
                str = days + "天前";
                return str;
            }
            int days_remains = totalSeconds % (3600 * 24);
            // 得到总小时数
            hours = days_remains / 3600;
            if (hours > 0) {
                str = hours + "小时前";
                return str;
            }
            int remains_hours = days_remains % 3600;
            // 得到分种数
            minutes = remains_hours / 60;
            if (minutes > 0) {
                str = minutes + "分钟前";
                return str;
            }
            // 得到总秒数
            seconds = remains_hours % 60;
            if (seconds > 0) {
                str = seconds + "秒前";
                return str;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    private static int tdays; // 天数
    private static int thours; // 时
    private static int tminutes; // 分
    private static int tseconds; // 秒

    public static String getQuott(String time1, String time2) {
        long quot = 0;
        String str = "";
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date1 = ft.parse(time1);
            Date date2 = ft.parse(time2);
            quot = date1.getTime() - date2.getTime();
            int totalSeconds = (int) (quot / 1000);
            // 得到总天数
            tdays = totalSeconds / (3600 * 24);
            if (tdays > 0) {
                str = tdays + "天";
                return str;
            }
            int days_remains = totalSeconds % (3600 * 24);
            // 得到总小时数
            thours = days_remains / 3600;
            if (thours > 0) {
                str = thours + "时";
                return str;
            }
            int remains_hours = days_remains % 3600;
            // 得到分种数
            tminutes = remains_hours / 60;
            if (tminutes > 0) {
                str = tminutes + "分";
                return str;
            }
            // 得到总秒数
            tseconds = remains_hours % 60;
            if (tseconds > 0) {
                str = tseconds + "秒";
                return str;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 判断日期时间段
     * 
     * @param date
     * @return
     */
    public static String excuteOneTime(String date) {
        StringBuffer restr = new StringBuffer();
        // date="2013-01-30 8:20:04";
        String[] a = date.split(" ");
        String str = a[1];
        String[] b = str.split(":");
        String b0 = b[0];
        String b1 = b[1];
        if (Integer.valueOf(b1) >= 0 && Integer.valueOf(b1) <= 30) {
            restr.append(b0 + ":00" + "-" + b0 + ":30");
        } else if (Integer.valueOf(b1) > 30) {
            int b_0 = Integer.valueOf(b0) + 1;
            restr.append(b0 + ":30" + "-" + Integer.valueOf(b_0) + ":00");
        }
        return restr.toString();
    }

    /**
     * 得到指定月的天数
     * */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 
     * 方法描述： 获取指定年月的最大天数，如 2016-06 返回 30
     *
     * @author: 李跃华
     * @date： 日期：2016-6-20 时间：上午10:53:45
     * @param yearAndMonth
     * @return
     * @version 4.3
     */
    public static int getMaxDayOfMonth(String yearAndMonth) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(stringToDate(yearAndMonth, "yyyy-MM"));
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到本月最后一个周日的日期
     * 
     * @param args
     * @throws Exception
     */
    public static String getMonthAndSunday(int year, int month) {
        String sundays = "";
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        SimpleDateFormat re = new SimpleDateFormat("yyyy-MM-dd");
        Calendar setDate = Calendar.getInstance();
        // 从第一天开始
        int day;
        int monthLastDay = getMonthLastDay(year, month);
        for (day = 1; day <= monthLastDay; day++) {
            setDate.set(Calendar.DATE, day);
            String str = sdf.format(setDate.getTime());
            if (str.equals("星期日")) {
                sundays = re.format(setDate.getTime());
            }
        }
        return sundays;
    }

    /**
     * 得到本月第一个周六日期
     * 
     * @param args
     * @throws Exception
     */
    public static String getFirstSaturdayOfWeek(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            cal.add(Calendar.DATE, 1);
        }
        Date date = cal.getTime();
        return dateToStr(date);
    }

    // （1）获得当前日期与本周一相差的天数
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
     * 获取时间提示信息，某一时间距离现在n秒、n分钟、n小时、n天.时间距现在比较精确到秒
     * 
     * @param args
     * @throws Exception
     */
    public static String getDateTips(Date beginDate) {
        Long now = (DateUtils.getNow().getTime()) / 1000L;
        Long sourse = beginDate.getTime() / 1000L;
        Long differ = now - sourse;
        String showDate = "";
        if (differ <= 60L) {
            // 0-60(包含)秒显示 秒之前
            showDate = differ + "秒前";
        } else if (differ <= (60L * 60L)) {
            // 如果是在60分钟内
            showDate = (int) (Math.floor(differ / 60L)) + "分钟前";
        } else if (differ <= 60L * 60L * 24) {
            // 如果是在1天内
            showDate = (int) (Math.floor(differ / (60L * 60L))) + "小时前";
        } else if (differ <= 60L * 60L * 24 * 5) {
            // 如果是在5天内
            showDate = (int) (Math.floor(differ / (60L * 60L * 24L))) + "天前";
        } else {
            Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            showDate = format.format(beginDate);
        }
        if (null != showDate && showDate.length() > 18 && showDate.contains(".")) {
            showDate = showDate.substring(0, showDate.length() - 2);
        }
        return showDate;
    }

    /**
     * 将长时间格式字符串转换为字符串 yyyy-MM-dd HH:mm:ss
     * 
     * @return
     * @throws ParseException
     */
    public static String longToStrng(String time) throws ParseException {
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        // dateFormat.setLenient(false);
        if ("".equals(time)) {
            return "";
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.parse(time);
            return dateToStrLong(strToDateLong(time));
        } catch (Exception e) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.parse(time);
                return dateToStrLong(strToDate(time));
            } catch (Exception e2) {
                try {
                    Date date = new Date(Long.parseLong(time.trim()));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(date);
                    // System.out.println("TIME:::" + dateString);
                    return dateString;
                } catch (Exception e3) {
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        return DateUtils.dateToStrLong(df.parse(time));
                    } catch (Exception e4) {
                        SimpleDateFormat parser = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZ yyyy", Locale.ENGLISH);
                        Date date = parser.parse(time);
                        // System.out.println(DateUtils.dateToStrLong(date));
                        return DateUtils.dateToStrLong(date);
                    }
                }
            }
        }
    }

    /**
     * 将长时间格式字符串转换为字符串 上午 HH:mm
     * 
     * @return
     * @throws ParseException
     */
    public static String dateToTime(Date date) throws ParseException {
        Date now = getNowDate();
        Date todayMiddle = strToDateLong(dateToStr(now) + " 12:00:00");
        if (getDays(now, date) != 0) {
            return dateToStrLong(date);
        }
        if (todayMiddle.before(date)) {
            Date d = strToDateLong(getPreTime(dateToStrLong(date), "-720"));
            return "下午  " + getTimeShorts(d);
        } else {
            return "上午  " + getTimeShorts(date);
        }
    }

    public static int isTodayCurrentTimeHour(String createTime) {
        int result = DateUtils.isTodayCurrentTime(DateUtils.stringToDate(createTime, ""));
        return result;
    }

    /**
     * 将字符型转换为指定格式日期型
     * 
     * @param _date 需要转换成日期的字符串
     * @param format 与需要转换成日期的字符串相匹配的格式
     * @return
     */
    public static Date stringToDate(String _date, String format) {
        if (null == format || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将日期型转换为指定格式的字符串
     * 
     * @param date 日期
     * @param format 格式
     * @return
     */
    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 时间判断，根据返回的标志位进行业务处理 (目前这个方法签到签退模块在使用)
     * 
     * @param args
     * @throws Exception
     */
    public static int isTodayCurrentTime(Date beginDate) {
        // 系统当前时间
        Long now = (DateUtils.getNow().getTime()) / 1000L;
        // 打卡系统传过来的时间
        Long sourse = beginDate.getTime() / 1000L;
        if (sourse > now) {
            return 2;
        }
        Long differ = now - sourse;
        int result = 0;// 时间标志位
        if (differ <= 60L) {
            // 0-60(包含)秒显示 秒之前
            result = 2;// 60秒之内
        } else if (differ <= (60L * 60L)) {
            // 如果是在60分钟内
            result = 2;
        } else if (differ <= 60L * 60L * 24) {
            // 如果是在1天内
            result = 3;
        }
        return result;
    }

    /**
     * 
     * 方法描述：今天星期几
     * 
     * @author: 陈辉鑫
     * @date： 日期：2015-5-14 时间：上午11:44:34
     * @return 返回英文的星期几
     * @version 2.9
     */
    public static String getWeekOfTodayStr() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String week = "";
        switch (dayOfWeek) {
            case 1:
                week = "Sunday";
                break;
            case 2:
                week = "Monday";
                break;
            case 3:
                week = "Tuesday";
                break;
            case 4:
                week = "Wednesday";
                break;
            case 5:
                week = "Thursday";
                break;
            case 6:
                week = "Friday";
                break;
            case 7:
                week = "Saturday";
                break;
        }
        return week;
    }

    /**
     * 
     * 方法描述：今天星期几
     * 
     * @author: 陈辉鑫
     * @date： 日期：2015-5-14 时间：下午04:40:25
     * @return 返回数字
     * @version 2.9
     */
    public static int getWeekOfTodayNum() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    /**
     * 
     * 方法描述: 参数日期星期几
     * 
     * @author 许谋钧
     * @date 日期:2016-6-16
     * @param @return
     * @version
     */
    public static int getWeekOfNum(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        return dayOfWeek == 0 ? 7 : dayOfWeek;
    }

    /**
     * 
     * 方法描述：格式化月份至分钟
     * 
     * @author: 陈辉鑫
     * @date： 日期：2015-5-14 时间：下午06:46:22
     * @return
     * @version 2.9
     */
    public static String strMonthToMin(String _date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日HH时mm分");
        Date date = null;
        String dateString = "";
        try {
            date = sdf.parse(_date);
            dateString = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static String getDayByWeek(int week) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setFirstDayOfWeek(Calendar.MONDAY); // set first day is monday
        cal.setTimeInMillis(System.currentTimeMillis());// set current time
        cal.set(Calendar.DAY_OF_WEEK, week);// Calendar.MONDAY
        // int day = cal.getTime().getDate();
        return format.format(cal.getTime());
    }

    /**
     * 
     * 方法描述：时间大小对比
     * 
     * @author: 陈辉鑫
     * @date： 日期：2015-5-14 时间：下午04:13:39
     * @param t1
     * @param t2
     * @return
     * @version 2.9
     */
    public static int timeCompare(String t1, String t2) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(formatter.parse(t1));
            c2.setTime(formatter.parse(t2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = c1.compareTo(c2);
        return result;
    }

    public static int daysxiangcha(String dateStr1, String dateStr2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateStr1 = sdf.format(sdf2.parse(dateStr1));
            dateStr2 = sdf.format(sdf2.parse(dateStr2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int year1 = Integer.parseInt(dateStr1.substring(0, 4));
        int month1 = Integer.parseInt(dateStr1.substring(4, 6));
        int day1 = Integer.parseInt(dateStr1.substring(6, 8));
        int year2 = Integer.parseInt(dateStr2.substring(0, 4));
        int month2 = Integer.parseInt(dateStr2.substring(4, 6));
        int day2 = Integer.parseInt(dateStr2.substring(6, 8));
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.YEAR, year1);
        c1.set(Calendar.MONTH, month1 - 1);
        c1.set(Calendar.DAY_OF_MONTH, day1);
        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, year2);
        c2.set(Calendar.MONTH, month2 - 1);
        c2.set(Calendar.DAY_OF_MONTH, day2);
        long mills = c1.getTimeInMillis() > c2.getTimeInMillis() ? c1.getTimeInMillis() - c2.getTimeInMillis() : c2
                    .getTimeInMillis() - c1.getTimeInMillis();
        return (int) (mills / 1000 / 3600 / 24);
    }

    /**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) w = 0;
        //
        return weekDays[w];
    }

    /**
     * 方法描述:获取对应的星期 1，2，3，4，5，6，7，
     * 
     * @param date
     * @return
     * @author 苏志芳 suzf
     * @date 2015-7-28 下午06:15:30
     * @version V1.1
     */
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0) {
            week = 7;
        }
        return week;
    }

    public static String newDate(String _date) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(_date);
            String newDate = sdf.format(date);
            return newDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间所在周的周一时间
     * 
     * @return
     */
    public static Date getMonday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00", Locale.CHINA);
        Calendar rl = Calendar.getInstance(Locale.CHINA);
        rl.setFirstDayOfWeek(Calendar.MONDAY);
        // 当前时间，貌似多余，其实是为了所有可能的系统一致
        // rl.setTimeInMillis(System.currentTimeMillis());
        // System.out.println("当前时间:"+format.format(rl.getTime()));
        // rl.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // System.out.println("周一时间:"+format.format(rl.getTime()));
        int mondayPlus = DateUtils.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        rl.setTime(monday);
        return DateUtils.strToDateLong(format.format(rl.getTime()));
    }

    /**
     * 方法描述:获取自然周第一天(周一)
     * 
     * @param date
     * @return
     */
    public static Date getMonday(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(date.getTime());
        cl.setFirstDayOfWeek(Calendar.MONDAY);
        cl.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date start = cl.getTime();
        return start;
    }

    /**
     * 方法描述:获取自然周最后一天(周日)
     * 
     * @param date
     * @return
     */
    public static Date getSunday(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(date.getTime());
        cl.setFirstDayOfWeek(Calendar.MONDAY);
        cl.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date end = cl.getTime();
        return end;
    }

    /**
     * 方法描述：获取自然周数和所在年份
     * 
     * @author: 苏志芳 suzf
     * @date： 日期：2015-11-17 时间：下午07:17:03
     * @param date
     * @return
     * @version 3.2.2
     */
    public static Map<String, Integer> getWeekNumAndYearNum(Date date) {
        Date monday = getMonday(date);
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setMinimalDaysInFirstWeek(7);
        cal.setTime(monday);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put(WEEKNUM, week);
        map.put(YEAR, year);
        return map;
    }

    /**
     * 获取某个时间在一年中的月份
     * 
     * @author qipeng.yan
     * @param date
     * @return
     * @date 2015-11-7 下午06:02:32
     * @version V1.0
     */
    public static int getMonthNumOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return (cal.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取间隔时间
     * 
     * @author qipeng.yan
     * @param type specifiedDay前还是后（前：DateUtils.TYPE_DATE_BEFORE; 后：DateUtils.TYPE_DATE_AFTER）
     * @param specifiedDay 时间字符串格式
     * @param interval 间隔多少天
     * @param format 格式化时间，和specifiedDay的时间格式一致
     * @return
     * @date 2015-11-9 上午09:09:48
     * @version V1.0
     */
    public static String getDayBeforeOrAfter(String type, String specifiedDay, int interval, String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getDayBeforeOrAfter(type, date, interval, format);
    }

    /**
     * 获取间隔时间
     * 
     * @author qipeng.yan
     * @param type type specifiedDay前还是后（前：DateUtils.TYPE_DATE_BEFORE; 后：DateUtils.TYPE_DATE_AFTER）
     * @param date 基准时间
     * @param interval 间隔多少天
     * @param format 格式化时间格式（如：yyyy-MM-dd）
     * @return
     * @date 2015-11-9 上午09:12:11
     * @version V1.0
     */
    public static String getDayBeforeOrAfter(String type, Date date, int interval, String format) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        if (TYPE_DATE_BEFORE.equals(type)) {
            c.set(Calendar.DATE, day - interval);
        } else if (TYPE_DATE_AFTER.equals(type)) {
            c.set(Calendar.DATE, day + interval);
        }
        String intervalDay = new SimpleDateFormat(format).format(c.getTime());
        return intervalDay;
    }

    public static Date getMonth(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(date.getTime());
        cl.set(Calendar.DAY_OF_MONTH, 1);
        Date end = cl.getTime();
        return end;
    }

    public static int getYear(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(date.getTime());
        int year = cl.get(Calendar.YEAR);
        return year;
    }

    /**
     * 一个时间向前推pre天后所在的year and weekNum
     * 
     * @author qipeng.yan
     * @param date 当前时间
     * @param pre 向前推几天
     * @return yw.put(YEAR, year); yw.put(WEEKNUM, weekNum);
     * @date 2015-11-15 下午03:02:02
     * @version V1.0
     */
    public static Map<String, Integer> getYearAndWeek(Date date, int pre) {
        String sDate = getDayBeforeOrAfter(DateUtils.TYPE_DATE_BEFORE, date, pre, "yyyy-MM-dd");
        Date preDate = formatStringToDate(sDate, "yyyy-MM-dd");
        return DateUtils.getWeekNumAndYearNum(preDate);
    }

    /**
     * 倒推多少天 yyyy-MM-dd
     * 
     * @author qipeng.yan
     * @param date
     * @param pre
     * @return
     * @date 2015-11-15 下午04:14:00
     * @version V1.0
     */
    public static Date getBeforeDate(Date date, int pre) {
        String dateStr = getDayBeforeOrAfter(DateUtils.TYPE_DATE_BEFORE, date, pre, "yyyy-MM-dd");
        return DateUtils.formatStringToDate(dateStr, "yyyy-MM-dd");
    }

    /**
     * 获取当前月第一天
     * 
     * @author qipeng.yan
     * @return
     * @date 2015-11-19 下午08:48:33
     * @version V1.0
     */
    public static Date getFirstOfMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        return formatStringToDate(first, "yyyy-MM-dd");
    }

    /**
     * 
     * 
     * 方法描述：返回每月的第一天（字符串）
     *
     * @author: 李跃华
     * @date：2016年8月16日 下午5:02:27
     * @return
     * @version 4.5
     */
    public static String getFirstDayOfMonthStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return format.format(c.getTime());
    }

    /**
     * 方法描述：String-->Timestamp
     * 
     * @author: 陈辉鑫
     * @date： 日期：2015-5-29 时间：下午05:36:03
     * @param str
     * @return
     * @version 2.9
     */
    public static Timestamp strToTimestamp(String str) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        try {
            ts = Timestamp.valueOf(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ts;
    }

    public static String lastYear(Date thisYear, int spaceYear) {
        Calendar c = Calendar.getInstance();
        c.setTime(thisYear);
        c.add(Calendar.YEAR, spaceYear);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String dateString = formatter.format(c.getTime());
        return dateString;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
                    .get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * 获取当前月最后一天
     * 
     * @author qipeng.yan
     * @return
     * @date 2015-11-19 下午08:48:24
     * @version V1.0
     */
    public static Date getLastOfMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        return formatStringToDate(last, "yyyy-MM-dd");
    }

    /**
     * 方法描述：是否是当前月份
     *
     * @author: 李跃华
     * @date： 日期：2016-6-20 时间：上午11:26:25
     * @param month
     * @return
     * @version 4.3
     */
    public static boolean isCurrentMonth(String month) {
        String currentMonth = dateToString(new Date(), "yyyy-MM");
        return StringUtils.equals(currentMonth, month);
    }

    /**
     * 方法描述：
     *
     * @author: 李跃华
     * @date： 日期：2016-6-20 时间：上午11:28:57
     * @return
     * @version 4.3
     */
    public static int getCurrentDayOfMonth(Date date) {
        Calendar ca = Calendar.getInstance();
        if (date == null) {
            date = new Date();
        }
        ca.setTime(date);
        return ca.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 方法描述：
     *
     * @author: 李跃华
     * @date： 日期：2016-6-20 时间：上午11:28:57
     * @return
     * @version 4.3
     */
    public static int getCurrentDayOfMonth() {
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        return ca.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 方法描述：
     *
     * @author: 李跃华
     * @date： 日期：2016-6-20 时间：下午02:18:46
     * @param today
     * @return
     * @version 4.3
     */
    public static Date dayStartTime(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 方法描述：
     *
     * @author: 李跃华
     * @date： 日期：2016-6-20 时间：下午02:22:28
     * @param today
     * @return
     * @version 4.3
     */
    public static Date dayEndTime(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 方法描述：时间转为年月的数字，如（201607）
     *
     * @author: 李跃华
     * @date： 日期：2016-7-13 时间：下午05:04:12
     * @param recodeTime
     * @return
     * @version 4.3
     */
    public static int getYearAndMonthInt(Date recodeTime) {
        if (recodeTime == null) {
            return 0;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        return Integer.parseInt(dateFormat.format(recodeTime));
    }

    public static Set<String> getDateRange(String dateFrom, String dateTo) {
        Set<String> range = new HashSet<String>();
        String dateBegin = dateFrom;
        while (dateBegin.compareTo(dateTo) <= 0) {
            if (!range.contains(dateBegin)) {
                range.add(dateBegin);
            }
            dateBegin = DateUtils.getNextDay(dateBegin, "1");
        }
        return range;
    }

    public static String getNeturalAge(String birthday) {
        Calendar calendarBirth = Calendar.getInstance();
        calendarBirth.setTime(DateUtils.strToDateShort(birthday));
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(DateUtils.getNowDateShort());
        StringBuilder result = new StringBuilder();
        int diffYears = 0, diffMonths, diffDays;
        int dayOfBirth = calendarBirth.get(Calendar.DAY_OF_MONTH);
        int dayOfNow = calendarNow.get(Calendar.DAY_OF_MONTH);
        if (dayOfBirth <= dayOfNow) {
            diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
            diffDays = dayOfNow - dayOfBirth;
            if (diffMonths == 0) diffDays++;
        } else {
            if (isEndOfMonth(calendarBirth)) {
                if (isEndOfMonth(calendarNow)) {
                    diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
                    diffDays = 0;
                } else {
                    calendarNow.add(Calendar.MONTH, -1);
                    diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
                    diffDays = dayOfNow + 1;
                }
            } else {
                if (isEndOfMonth(calendarNow)) {
                    diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
                    diffDays = 0;
                } else {
                    calendarNow.add(Calendar.MONTH, -1);// 上个月
                    diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
                    // 获取上个月最大的一天
                    int maxDayOfLastMonth = calendarNow.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (maxDayOfLastMonth > dayOfBirth) {
                        diffDays = maxDayOfLastMonth - dayOfBirth + dayOfNow;
                    } else {
                        diffDays = dayOfNow;
                    }
                }
            }
        }
        // 计算月份时，没有考虑年
        diffYears = diffMonths / 12;
        diffMonths = diffMonths % 12;
        if (diffYears > 0) {
            result.append(diffYears + "岁");
        }
        if (diffMonths > 0) {
            result.append(diffMonths + "个月");
        }
        if (diffDays > 0) {
            result.append(diffDays + "天");
        }
        return result.toString();
    }

    /**
     * 获取两个日历的月份之差
     * 
     * @param calendarBirth
     * @param calendarNow
     * @return
     */
    public static int getMonthsOfAge(Calendar calendarBirth, Calendar calendarNow) {
        return (calendarNow.get(Calendar.YEAR) - calendarBirth.get(Calendar.YEAR)) * 12
                    + calendarNow.get(Calendar.MONTH) - calendarBirth.get(Calendar.MONTH);
    }

    /**
     * 判断这一天是否是月底
     * 
     * @param calendar
     * @return
     */
    public static boolean isEndOfMonth(Calendar calendar) {
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfMonth == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) return true;
        return false;
    }
}
