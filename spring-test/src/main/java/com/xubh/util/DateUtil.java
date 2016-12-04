package com.xubh.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>
 * 日期时间工具类，提供对日期和时间进行各种运算操作的支持
 * </p>
 */
public class DateUtil {
    /**
     * 1000毫秒*60秒*60分*24小时=一天毫秒数
     */
    public static final long MILLISECOND_DAY = 86400000;

    /**
     * 1年=31536000秒
     */
    public static final int SECOND_YEAR = 31536000;

    /**
     * 将日期类型转换为字符类型
     *
     * @param date
     * @param pattern
     * @return String
     */
    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * calendar类型转化为String类型
     *
     * @param calendar
     * @return
     */
    public static String convertCalendar(Calendar calendar) {
        return format(new Date(calendar.getTimeInMillis()));
    }

    /**
     * 将日期类型转换为字符类型[19位：yyyy-MM-dd HH:mm:ss]
     *
     * @param date
     * @return String
     */
    public static String format(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期类型为订单号需求的特殊形式
     *
     * @param date
     * @return
     */
    public static String formatForNO(Date date) {
        return format(date, "yyyyMMddHHmm");
    }

    public static String getNowDate(String formatter) {
        return format(DateUtil.getDate(), formatter);
    }

    /**
     * 将字符类型转换为sql类日期类型
     *
     * @param date
     * @param pattern
     * @return java.sql.Date
     * @throws ParseException
     */
    public static java.sql.Date parse(String date, String pattern) throws ParseException {
        return convert(new SimpleDateFormat(pattern).parse(date));
    }

    /**
     * 将字符类型[19位：yyyy-MM-dd HH:mm:ss]转换为日期类型
     *
     * @param date
     * @return java.sql.Date
     * @throws ParseException
     */
    public static java.sql.Date parse19(String date) throws ParseException {
        return parse(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将字符类型[10位：yyyy-MM-dd HH:mm:ss]转换为日期类型
     *
     * @param date
     * @return java.sql.Date
     * @throws ParseException
     */
    public static java.sql.Date parse10(String date) throws ParseException {
        return parse(date, "yyyy-MM-dd");
    }

    /**
     * 将util类日期转换为sql类日期
     *
     * @param date
     * @return java.sql.Date
     */
    public static java.sql.Date convert(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 获得当前日期时间
     *
     * @return Timestamp
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * 获得当前日期
     *
     * @return java.sql.Date
     */
    public static java.sql.Date getDate() {
        return convert(new Date());
    }

    /**
     * 获得从现在开始步进<b>天</b>数后的日期，如-1则昨天、2则后天，以此类推
     *
     * @param amount
     * @return java.sql.Date
     */
    public static java.sql.Date getStepDay(int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, amount);
        return convert(calendar.getTime());
    }

    /**
     * 获得从指定日期开始步进<b>天</b>数后的日期，如-1则昨天、2则后天，以此类推
     *
     * @param amount
     * @return java.sql.Date
     */
    public static java.sql.Date getStepDay(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, amount);
        return convert(calendar.getTime());
    }

    /**
     * 获得从现在开始步进<b>周</b>数后的日期，如-1则上周、2则下下周，以此类推
     *
     * @param amount
     * @return java.sql.Date
     */
    public static java.sql.Date stepWeekOfMonth(int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.WEEK_OF_MONTH, amount);
        return convert(calendar.getTime());
    }

    /*
     * 获得从现在开始步进<b>周</b>数后的日期，如-1则上周、2则下下周，以此类推
     * @param date
     * @param amount
     * @return java.sql.Date
     */
    public static java.sql.Date stepWeekOfMonth(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_MONTH, amount);
        return convert(calendar.getTime());
    }

    /**
     * 获得从现在开始步进<b>周</b>数后的日期，如-1则上周、2则下下周，以此类推
     *
     * @param date
     * @param amount
     * @return java.sql.Date
     */
    public static java.sql.Date stepWeekOfMonth(String date, int amount) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        try {
            cal.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stepWeekOfMonth(cal.getTime(), amount);
    }

    /**
     * 获得从现在开始步进<b>月</b>数后的日期，如-1则上月、2则下下月，以此类推
     *
     * @param amount
     * @return java.sql.Date
     */
    public static java.sql.Date getStepMonth(int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, amount);
        return convert(calendar.getTime());
    }

    public static java.sql.Date getStepMonth(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);
        return convert(calendar.getTime());
    }

    /**
     * 日期相减，返回长整型数值
     *
     * @param begin
     * @param end
     * @return long
     */
    public static long subDate(Date begin, Date end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(begin);
        long beginTime = calendar.getTimeInMillis();
        calendar.setTime(end);
        long endTime = calendar.getTimeInMillis();
        return beginTime - endTime;
    }

    /**
     * 日期相减，返回长整型数值
     *
     * @param begin
     * @param end
     * @return long
     */
    public static long subDate(String begin, String end) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calBegin = new GregorianCalendar();
        Calendar calEnd = new GregorianCalendar();
        try {
            calBegin.setTime(format.parse(begin));
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            calEnd.setTime(format.parse(end));
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        long beginTime = calBegin.getTimeInMillis();
        long endTime = calEnd.getTimeInMillis();
        return beginTime - endTime;
    }

    /**
     * 返回日期指示一个星期中的某天
     *
     * @param date
     * @param amount
     * @return
     */
    public static int getDayOfWeek(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_WEEK, amount);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 始终返回某日期一周中的星期几
     *
     * @param toDay4 以某日期为基准
     * @param i      返回星期几
     * @return java.sql.Date
     * <p>
     * <ul>
     * <li>
     * Example: getStepBeforeDay(new Date(),0) 返回当前日期为基本的上周日</li>
     * </ul>
     * </p>
     */
    public static Date getStepBeforeDay(Date toDay4, int i) {
        Date edate = null;
        int dayOfWeek = DateUtil.getDayOfWeek(toDay4, -1);
        switch (dayOfWeek) {
            case 1:
                edate = DateUtil.getStepDay(toDay4, i - 1);
                break;
            case 2:
                edate = DateUtil.getStepDay(toDay4, i - 2);
                break;
            case 3:
                edate = DateUtil.getStepDay(toDay4, i - 3);
                break;
            case 4:
                edate = DateUtil.getStepDay(toDay4, i - 4);
                break;
            case 5:
                edate = DateUtil.getStepDay(toDay4, i - 5);
                break;
            case 6:
                edate = DateUtil.getStepDay(toDay4, i - 6);
                break;
            default:
                edate = toDay4;
        }
        return edate;
    }

    public static Calendar stringformatToCalendar(String date) {
        Calendar calendar;
        try {
            calendar = GregorianCalendar.getInstance();
            calendar.setTimeInMillis(parse(date, "yyyy-MM-dd HH:mm:ss").getTime());
        } catch (Exception e2) {
            e2.printStackTrace();
            calendar = null;
        }
        return calendar;
    }

    /**
     * 查看当前时间是否在优惠时间范围内
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean compareTime(String start, String end) {
        try {
            long t1 = DateUtil.parse(start, "yyyy-MM-dd HH:mm:ss").getTime();
            long t2 = DateUtil.parse(end, "yyyy-MM-dd HH:mm:ss").getTime();
            long t3 = (new Date()).getTime();
            if (t3 > t1 && t3 < t2) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 比较2个时间点先后，返回时间点靠后的
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String compareDateForString19(String date1, String date2) {
        if (date1 == null && date2 == null) {
            return DateUtil.format(new Date());
        } else if (date1 == null) {
            return date2;
        } else if (date2 == null) {
            return date1;
        } else {
            try {
                long l1 = DateUtil.parse19(date1).getTime();
                long l2 = DateUtil.parse19(date2).getTime();
                String date = l1 - l2 > 0 ? date1 : date2;
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //如果时间判断出错，直接从最新时间开始计算
            return DateUtil.format(new Date());
        }
    }

    /**
     * 从指定时间计算用户购买时间
     *
     * @param timelimit
     * @return
     */
    public static String addTimeLimit(int timelimit, String date) {
        Calendar current_calendar = DateUtil.stringformatToCalendar(date);
        current_calendar.add(Calendar.DAY_OF_MONTH, timelimit);
        return DateUtil.convertCalendar(current_calendar);
    }

    /**
     * long转字符串
     *
     * @param timelimit
     * @return
     */
    public static String longTimeChangeStr(Long timelimit) {
        Calendar current_calendar = new GregorianCalendar();
        current_calendar.setTimeInMillis(timelimit);
        return DateUtil.convertCalendar(current_calendar);
    }

    /**
     * 字符串转LONG
     *
     * @param time
     * @return
     */
    public static Long strTimeChangeLong(String time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calBegin = new GregorianCalendar();
        try {
            calBegin.setTime(format.parse(time));
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        long beginTime = calBegin.getTimeInMillis();
        return beginTime;
    }

    /**
     * 检验时间格式
     *
     * @param time      时间
     * @param formatter 格式字符串
     * @return
     */
    public static Boolean checkTimeFormat(String time, String formatter) {
        DateFormat format = new SimpleDateFormat(formatter);
        try {
            if (time.length() == formatter.length()) {
                format.parse(time);
                return true;
            }
        } catch (ParseException e) {
            return false;
        }
        return false;
    }


    public static int Hour(Date time) {
        SimpleDateFormat st = new SimpleDateFormat("yyyyMMddHH");
        return Integer.parseInt(st.format(time));
    }

    public static Date StringToDate(String s) {
        Date time = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            time = sd.parse(s);
        } catch (Exception e) {
            System.out.println("输入的日期格式有误！");
        }
        return time;
    }

    public static String subTime(String begin, String end) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calBegin = new GregorianCalendar();
        Calendar calEnd = new GregorianCalendar();
        try {
            calBegin.setTime(format.parse(begin));

        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            calEnd.setTime(format.parse(end));
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        StringBuffer sb = new StringBuffer("");
        long temp = calEnd.getTimeInMillis() - calBegin.getTimeInMillis();
        if (86400000 < temp) {
            //sb.append((temp/86400000) + "天");
            temp = temp % 86400000;
        }
        if (3600000 < temp) {
            long hour = (temp / 3600000);
            sb.append((((hour + "").length() == 1 ? "0" + hour : hour)) + ":");
            temp = temp % 3600000;
        } else {
            sb.append("00:");
        }
        if (60000 < temp) {
            long min = (temp / 60000);
            if (min == 60) {
                min = min - 1;
            }
            sb.append((((min + "").length() == 1 ? "0" + min : min)) + ":");
            temp = temp % 60000;
        } else {
            sb.append("00:");
        }
        if (1000 < temp) {
            long second = temp / 1000;
            if (second == 60) {
                second = second - 1;
            }
            sb.append(((second + "").length() == 1 ? "0" + second : second));
        } else {
            sb.append("00");
        }
        return sb.toString();
    }

    /**
     * 时间戳转Date对象
     *
     * @param timestamp
     * @return
     */
    public static Date getTimestampToDate(long timestamp) {
        Date date = new Date(timestamp);
        return date;
    }
}
