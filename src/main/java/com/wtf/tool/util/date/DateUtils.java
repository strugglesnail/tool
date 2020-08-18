package com.wtf.tool.util.date;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wang_tengfei
 * @desc 时间工具类
 */
public class DateUtils {

    // 默认返回6个日期
    private static final int DATE_NUM = -6;

    /**
     * @param yearMonth
     * @return
     */
    public static List<String> getDefaultYearMonthDate(String yearMonth) {
        return getYearMonthDateByNum(yearMonth, DATE_NUM);
    }

    public static List<String> getYearMonthDateByNum(String yearMonth, int num) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        // 判断指定的日期是前还是后：1 前  -1 后
        int index = 1;
        if (num < 0) {
            index = -1;
        }
        List<String> dateList = new ArrayList<>(Math.abs(num));
        try {
            calendar.setTime(format.parse(yearMonth));
            for (int i = 0; i < Math.abs(num); i++) {
                if (i == 0) {
                    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 0);
                } else {
                    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + index);
                }
                String dateFormat = format.format(calendar.getTime());
                dateList.add(dateFormat);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Collections.reverse(dateList);
        return dateList;
    }


    public static void main(String[] args) {
        System.out.println(getYearMonthDateByNum("2020-06-18", -6));
        System.out.println(getDefaultYearMonthDate("2020-06-18"));
    }
}
