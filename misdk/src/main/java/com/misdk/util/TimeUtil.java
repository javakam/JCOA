package com.misdk.util;

import android.text.TextUtils;
import android.util.Log;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间工具类<p>
 * Created by javakam on 2016/7/8.
 */
public class TimeUtil {

    //------------------------------------------------OA--------------------------------------------------//

    /**
     * 标题显示时间
     *
     * @return 今日：2017年5月15日  星期一
     */
    public static String getTitleDate() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return "今日: " + mYear + "年" + mMonth + "月" + mDay + "日  " + "星期" + mWay;
    }

    /**
     * 发布日志时默认进入页面显示时间为今天
     *
     * @return 格式：   2017.5.15
     */
    public static String getLogDefaultDate(int type) {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if (type == 0) {
            return mYear + "." + mMonth + "." + mDay;
        } else {
            return mYear + "-" + mMonth + "-" + mDay;
        }
    }

    /**
     * 发布日志时默认进入页面显示时间为今天
     *
     * @return 格式：   2017.5.15
     */
    public static String getLastMonthDate() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH));// 获取当前月份 去掉 + 1
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        return mYear + "." + mMonth + "." + mDay;
    }
    //--------------------------------------------------------------------------------------------------//

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm
     */
    public static String getNowDateMinute() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 服务器上的时间转换成客户端时间
     * ╮(╯▽╰)╭   8.1--php server--168035*--now--168035000
     *
     * @param times PHP 服务器返回时间  eg : 4026   3897
     * @return 4026000  3897000
     */
    public static long serverToClientTime(String times) {
        if (TextUtils.isEmpty(times)) return 0;

        Calendar serverNow = Calendar.getInstance();
        //从PHP转成Java的时间值,在末尾添加三位
        try {
            serverNow.setTime(new Date(Long.parseLong(times) * 1000));
        } catch (NumberFormatException e) {
            return 0;
        }
        return serverNow.getTimeInMillis();
    }

    /**
     * 获取转化成11261000的当前时间(当前时间的格式为HH:mm:ss)
     * eg : ╮(╯▽╰)╭   8.1--php server--168035*--now--168035000
     *
     * @return 返回格式 11261000
     */
    public static long getNowDateHourBySecond() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String date = formatter.format(currentTime);

        Date date2 = null;
        try {
            date2 = formatter.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date2);
            return cal.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
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
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateyyyyMMdd() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDateSecond() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取当前时间
     *
     * @return 返回格式 yyyy-MM-dd HH:mm
     */
    public static String getStringDateMinute() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = formatter.format(currentTime);
        return date;
    }

    /**
     * 获取当前时间
     *
     * @return 返回格式 yyyy-MM-dd
     */
    public static String getStringDateyyyy_MM_dd() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(currentTime);
        return date;
    }

    /**
     * 获取当前时间2
     *
     * @return 返回格式 yyyy.MM.dd
     */
    public static String getStringDateyyyyMMdd2() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        String date = formatter.format(currentTime);
        return date;
    }

    /**
     * 获取前月的第一天
     *
     * @return yyyy.MM.dd
     */
    public static String getFirstDayOfThisMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return format.format(cale.getTime());
    }

    /**
     * 获取前月的最后一天
     *
     * @return yyyy.MM.dd
     */
    public static String getLastDayOfThisMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return format.format(cale.getTime());
    }

    /**
     * 格式化时间
     *
     * @param date
     * @return 返回格式 yyyy-MM-dd
     */
    public static String formateDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(date);
        return s;
    }

    /**
     * 两时间比较大小
     *
     * @param setDate
     * @param nowDate
     * @return true setDate < nowDate
     */
    public static boolean compared(String setDate, String nowDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = dateFormat.parse(setDate);
            Date date2 = dateFormat.parse(nowDate);
            if (date1.getTime() < date2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 将日期格式转为毫秒数
     *
     * @param in 格式为 2014-09-30
     * @return 返回格式为 1345185923140
     */
    public static long dateToLong(String in) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(in);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 将日期格式转为毫秒数
     *
     * @param in 格式为 2014年9月30日
     * @return 返回格式为 1345185923140
     */
    public static long dateToLong3(String in) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年M月dd日");
        try {
            Date date = format.parse(in);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 将日期格式转为毫秒数
     *
     * @param in 格式为 2014-09-30 09:50
     * @return 返回格式为 1345185923140
     */
    public static long dateToLong1(String in) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = format.parse(in);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 将日期格式转为毫秒数
     *
     * @param in 格式为 2014年09月30日 09:50
     * @return 返回格式为 1345185923140
     */
    public static long dateToLong2(String in) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {
            Date date = format.parse(in);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 将日期格式转为毫秒数
     *
     * @param in 格式为 2014-09-30 09:50:30
     * @return 返回格式为 1345185923140
     */
    public static long dateToLong4(String in) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(in);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 将日期格式转为毫秒数
     *
     * @param in 格式为 2014-09-30
     * @return 返回格式为 1345185923140
     */
    public static long dateToLong5(String in) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(in);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将日期格式转为毫秒数
     *
     * @param in 格式为 2014.09.30
     * @return 返回格式为 1345185923140
     */
    public static long dateToLong6(String in) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date = format.parse(in);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将毫秒数转为日期
     *
     * @param millis 格式为1345185923140L
     * @return 返回格式为 年-月-日 时：分：秒
     */
    public static String longToDate(long millis) {
        Date date = new Date(millis);
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sb = format.format(gc.getTime());
        System.out.println(sb);
        return sb;
    }

    /**
     * 将long数转为日期
     *
     * @param millis 格式为1345185923140
     * @return 返回格式为 年-月-日 时：分
     */
    public static String longToDate1(long millis) {
        Date date = new Date(millis);
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sb = format.format(gc.getTime());
        System.out.println(sb);
        return sb;
    }

    /**
     * 将毫秒数转为日期
     *
     * @param millis 格式为1345185923140L
     * @return 返回格式为 年-月-日
     */
    public static String longToDate2(long millis) {
        Date date = new Date(millis);
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String sb = format.format(gc.getTime());
        System.out.println(sb);
        return sb;
    }

    /**
     * 将毫秒数转为日期
     *
     * @param millis 格式为1345185923140L
     * @return 返回格式为 2014年09月07日 10:30
     */
    public static String longToDate3(long millis) {
        Date date = new Date(millis);
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String sb = format.format(gc.getTime());
        System.out.println(sb);
        return sb;
    }

    /**
     * 将毫秒数转为日期
     *
     * @param millis 格式为1345185923140L
     * @return 返回格式为 2014年09月07日
     */
    public static String longToDate4(long millis) {
        Date date = new Date(millis);
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String sb = format.format(gc.getTime());
        System.out.println(sb);
        return sb;
    }

    /**
     * 将毫秒数转为日期
     *
     * @param millis 格式为1345185923140L
     * @return 返回格式为 2014-09-07-10-30-10
     */
    public static String longToDate5(long millis) {
        Date date = new Date(millis);
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String sb = format.format(gc.getTime());
        System.out.println(sb);
        return sb;
    }

    /**
     * 将毫秒数转为日期
     *
     * @param millis 格式为1345185923140L
     * @return 返回格式为 20140907103010
     */
    public static String longToDate6(long millis) {
        Date date = new Date(millis);
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String sb = format.format(gc.getTime());
        System.out.println(sb);
        return sb;
    }

    /**
     * 将毫秒数转为日期
     *
     * @param millis 格式为1493913600000
     * @return 返回格式为 2017.05.22
     */
    public static String longToDate7(long millis) {
        Date date = new Date(millis);
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(gc.getTime());
    }

    /**
     * 将毫秒数转为日期
     *
     * @param millis 格式为1493913600000
     * @return 返回格式为 2017-05-22
     */
    public static String longToDate8(long millis) {
        Date date = new Date(millis);
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(gc.getTime());
    }

    /**
     * 转换剩余时间
     *
     * @param millis
     * @return
     */
    public static String getEndTime(String millis) {
        long t = Long.parseLong(millis);
        Date date = new Date(t);
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String sb = format.format(gc.getTime());
        Log.e("123", sb + "---剩余时间");
        return sb;
    }

    // /**
    // * 实现将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒,并返回成yyyy-MM-dd-HH-mm-ss各式
    // *
    // * @param initDateTime
    // * 初始日期时间值 字符串型
    // * @return Calendar
    // */
    // public static String getCalendarByInintData(String initDateTime) {
    // if (null != initDateTime && initDateTime.length() > 0) {
    // // 将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒
    // String date = DateTimePickDialogUtil.spliteString(initDateTime,
    // "日", "index", "front");
    // String time = DateTimePickDialogUtil.spliteString(initDateTime,
    // "日", "index", "back");
    //
    // String yearStr = DateTimePickDialogUtil.spliteString(date, "年",
    // "index", "front");
    // String monthAndDay = DateTimePickDialogUtil.spliteString(date, "年",
    // "index", "back");
    //
    // String monthStr = DateTimePickDialogUtil.spliteString(monthAndDay,
    // "月", "index", "front");
    // String dayStr = DateTimePickDialogUtil.spliteString(monthAndDay,
    // "月", "index", "back");
    //
    // String hourStr = DateTimePickDialogUtil.spliteString(time, ":",
    // "index", "front");
    // String minuteStr = DateTimePickDialogUtil.spliteString(time, ":",
    // "index", "back");
    //
    // int currentYear = Integer.valueOf(yearStr.trim()).intValue();
    // int currentMonth = Integer.valueOf(monthStr.trim()).intValue();
    // int currentDay = Integer.valueOf(dayStr.trim()).intValue();
    // int currentHour = Integer.valueOf(hourStr.trim()).intValue();
    // int currentMinute = Integer.valueOf(minuteStr.trim()).intValue();
    //
    // String addMonth = String.valueOf(currentMonth);
    // if (currentMonth < 10) {
    // addMonth = "0" + addMonth;
    // }
    //
    // String addDay = String.valueOf(currentDay);
    // if (currentDay < 10) {
    // addDay = "0" + addDay;
    // }
    //
    // String addHour = String.valueOf(currentHour);
    // if (currentHour < 10) {
    // addHour = "0" + addHour;
    // }
    //
    // String addMinute = String.valueOf(currentMinute);
    // if (currentMinute < 10) {
    // addMinute = "0" + addMinute;
    // }
    //
    // String returnStr = currentYear + "-" + addMonth + "-" + addDay
    // + "-" + addHour + "-" + addMinute + "-" + "00";
    // return returnStr;
    // } else {
    // return "";
    // }
    // }

    public static String formatTime(String dateTimeStr) {
        if ((null != dateTimeStr) && (dateTimeStr.length() > 0)) {
            String[] strs = dateTimeStr.split("-");
            String newStr = strs[3] + ":" + strs[4];
            return newStr;
        } else {
            return null;
        }
    }

    /**
     * 将2015-10-18-16-47-30格式时间转换为 2015年10月18日 16:47
     *
     * @param dateTime
     * @return
     */
    public static String formatTime1(String dateTime) {
        if (!TextUtils.isEmpty(dateTime)) {
            String[] strs = dateTime.split("-");
            String newStr = strs[0] + "年" + strs[1] + "月" + strs[2] + "日    " + strs[3] + ":" + strs[4];
            return newStr;
        } else {
            return "";
        }
    }

    /**
     * 将2015-10-18-16-47-30格式时间转换为 2015-10-18 16:47
     *
     * @param dateTime
     * @return
     */
    public static String formatTime2(String dateTime) {
        if (!TextUtils.isEmpty(dateTime)) {
            String[] strs = dateTime.split("-");
            String newStr = strs[0] + "-" + strs[1] + "-" + strs[2] + "  " + strs[3] + ":" + strs[4];
            return newStr;
        } else {
            return "";
        }
    }

    /**
     * 将2015-10-18-16-47-30格式时间转换为 2015-10-18
     *
     * @param dateTime
     * @return
     */
    public static String formatTime3(String dateTime) {
        if (!TextUtils.isEmpty(dateTime)) {
            String[] strs = dateTime.split("-");
            String newStr = strs[0] + "-" + strs[1] + "-" + strs[2];
            return newStr;
        } else {
            return "";
        }
    }

    /**
     * 将 yyyy-MM-dd 格式时间转换为 yyyy.MM.dd
     *
     * @param dateTime
     * @return
     */
    public static String formatTime4(String dateTime) {
        if (!TextUtils.isEmpty(dateTime)) {
            String newTime = dateTime.replace("-", ".");
            return newTime;
        } else {
            return "";
        }
    }

    /**
     * 将 yyyy-MM-dd HH:mm:ss 格式时间转换为 yyyy.MM.dd
     *
     * @param dateTime
     * @return
     */
    public static String formatTime5(String dateTime) {
        if (!TextUtils.isEmpty(dateTime)) {
            String newTime = dateTime.substring(0, 10);
            return newTime.replace("-", ".");
        } else {
            return "";
        }
    }

    /**
     * 将MaterialCalendarView上点击返回的Date格式时间  转换为 yyyy-MM-dd
     *
     * @return 返回格式 yyyy-MM-dd
     */
    public static String formatTime6(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * 将MaterialCalendarView上点击返回的Date格式时间  转换为  yyyy.MM.dd
     *
     * @return 返回格式  yyyy.MM.dd
     */
    public static String formatTime7(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        return formatter.format(date);
    }

    /**
     * 服务器返回时间格式：2015/10/9 0:00:00
     *
     * @param time
     * @return 2015-10-09
     */
    public static String convertTime(String time) {

        if (!TextUtils.isEmpty(time)) {
            String str[] = time.split(" ");
            if (str.length > 1) {
                str[0] = str[0].replaceAll("/", "-");
                String s[] = str[0].split("-");
                String res = null;
                if (s.length == 3) {
                    res = s[0];
                    if (s[1].length() == 1) {
                        res += "-0" + s[1];
                    } else {
                        res += "-" + s[1];
                    }
                    if (s[2].length() == 1) {
                        res += "-0" + s[2];
                    } else {
                        res += "-" + s[2];
                    }
                }
                return res;
            }
        }
        return null;
    }

    /**
     * 将本地的时间转换为云端的时间
     */
    public static String convertNativeTimeToCloudTime(String timeStr) {
        if (!TextUtils.isEmpty(timeStr)) {

            String[] tempStrs = timeStr.split("-");
            int year = Integer.parseInt(tempStrs[0]);
            int month = Integer.parseInt(tempStrs[1]);
            int day = Integer.parseInt(tempStrs[2]);
            String hour = "00";
            String minute = "00";
            String second = "00";
            if (tempStrs.length == 6) {
                hour = tempStrs[3];
                minute = tempStrs[4];
                second = tempStrs[5];
            }
            if (minute.length() == 1) {
                minute = "0" + minute;
            }
            if (second.length() == 1) {
                second = "0" + second;
            }
            String finalTime = year + "/" + month + "/" + day + " " + hour + ":" + minute + ":" + second;
            return finalTime;
        } else {
            return "";
        }
    }

    /**
     * 获取MD5加密的字符串
     *
     * @param str
     * @return
     */
    public static String md5Encrypt(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
