package com.project.tutoronline.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtil {

    private static final String PATTERN = "yyyy-MM-dd";

    public boolean compareDate(String endDate, String startDate) {
        if (!endDate.isEmpty() || !startDate.isEmpty()) {
            Date end = convertStringToDate(endDate, PATTERN);
            Date start = convertStringToDate(startDate, PATTERN);
            return start.before(end);
        }
        return false;
    }

    public static Date convertStringToDate(String value, String pattern) {
        try {
            if (value != null) {
                return new SimpleDateFormat(pattern).parse(value);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertDateToString(Date value, String pattern) {
        return new SimpleDateFormat(pattern).format(value);
    }

    public boolean compareDate(Date startDate, Date endDate) {
        return startDate.before(endDate);
    }

    public static boolean compareEndDate(Date dateInput) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        int checkResult = dateInput.compareTo(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 30);
        Date date30 = calendar.getTime();

        int checkDate = dateInput.compareTo(date30);
        return checkResult >= 0 && checkDate <= 0;
    }

    public static boolean compareStartDate(Date dateInput) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String strDate = formatter.format(today);

        Date date = DateUtil.convertStringToDate(strDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        int checkResult1 = dateInput.compareTo(date);
        return checkResult1 >= 0;
    }

    public static boolean compareStartDateEndDate(Date startDate, Date endDate){
        int result = startDate.compareTo(endDate);
        return result <= 0;
    }

}
