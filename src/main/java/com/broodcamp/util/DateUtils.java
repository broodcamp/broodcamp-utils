package com.broodcamp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 **/
public class DateUtils {

    public static final Pattern fourDigitsPattern = Pattern.compile("(?<!\\d)\\d{4}(?!\\d)");
    public static final Pattern monthPattern = Pattern.compile("(?<!\\d)[0-1][0-9](?!\\d)");
    public static final Pattern dayPattern = Pattern.compile("(?<!\\d)\\d{2}(?!\\d)");
    public static final String SDF_STRING = "yyyy-MM-dd";
    public SimpleDateFormat sdFormat = new SimpleDateFormat(SDF_STRING);

    private DateUtils() {

    }

    public static boolean isPeriodsOverlap(Date periodStart, Date periodEnd, Date checkStart, Date checkEnd) {
        if ((checkStart == null && checkEnd == null) || (periodStart == null && periodEnd == null)) {
            return true;
        }

        // Period is not after dates being checked
        if (checkStart == null && (periodStart == null || (checkEnd != null && periodStart.compareTo(checkEnd) < 0))) {
            return true;

            // Period is not before dates being checked
        } else if (checkEnd == null && (periodEnd == null || (checkStart != null && periodEnd.compareTo(checkStart) > 0))) {
            return true;

            // Dates are not after period
        } else if (periodStart == null && (checkStart == null || (periodEnd != null && checkStart.compareTo(periodEnd) < 0))) {
            return true;

            // Dates are not before period
        } else if (periodEnd == null && (checkEnd == null || (periodStart != null && checkEnd.compareTo(periodStart) > 0))) {
            return true;

        } else if (checkStart != null && checkEnd != null && periodStart != null && periodEnd != null) {

            // Dates end or start within the period
            if ((checkEnd.compareTo(periodEnd) <= 0 && checkEnd.compareTo(periodStart) > 0) || (checkStart.compareTo(periodEnd) < 0 && checkStart.compareTo(periodStart) >= 0)) {
                return true;
            }

            // Period end or start within the dates
            if ((periodEnd.compareTo(checkEnd) <= 0 && periodEnd.compareTo(checkStart) > 0) || (periodStart.compareTo(checkEnd) < 0 && periodStart.compareTo(checkStart) >= 0)) {
                return true;
            }
        }
        return false;
    }

    public static String formatDateWithPattern(Date value, String pattern) {
        if (value == null) {
            return "";
        }
        String result = null;

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            result = sdf.format(value);

        } catch (Exception e) {
            result = "";
        }

        return result;
    }

    public static Date parseDateWithPattern(String dateValue, String pattern) {
        if (dateValue == null || dateValue.trim().length() == 0) {
            return null;
        }
        Date result = null;

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            result = sdf.parse(dateValue);

        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    public static Date truncateDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date truncateDate(Date date, ChronoUnit unit) {
        return new Date(OffsetDateTime.now().truncatedTo(unit).toInstant().toEpochMilli());
    }

    public static int daysInMonth(int year, int month) {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        return yearMonthObject.lengthOfMonth();
    }

    /**
     * Guess a date from a given string object
     * 
     * @param stringDate Date as a string or a timestamp number
     * @param hints      Date formats to consider
     * @return A date object
     */
    public static Date guessDate(String stringDate, String... hints) {

        if (stringDate == null) {
            return null;
        }

        Date result = null;
        stringDate = stringDate.trim();

        // First check if it is not a timestamp number
        try {

            long timeStamp = Long.parseLong(stringDate);
            if (stringDate.equals(timeStamp + "")) {
                return new Date(timeStamp);
            }

        } catch (Exception e) {
            // ignore any exception
        }

        // Try different formats
        for (String hint : hints) {
            SimpleDateFormat sdf = new SimpleDateFormat(hint);
            try {
                result = sdf.parse(stringDate);

            } catch (ParseException e) {
            }
            if (result != null) {
                return result;
            }
        }
        // test if the string contains a sequence of 4 digits
        final Matcher fourDigitsMatcher = fourDigitsPattern.matcher(stringDate);
        if (fourDigitsMatcher.find()) {
            String year = fourDigitsMatcher.group();
            // test if we have something that match a month
            String dayMonth = stringDate.substring(4);
            if (stringDate.indexOf(year) > 0) {
                dayMonth = stringDate.substring(0, stringDate.length() - 4);
            }
            final Matcher monthMatcher = monthPattern.matcher(dayMonth);
            if (monthMatcher.find()) {
                String month = monthMatcher.group();
                // if some other 2 digit also match month we cannot guess for sure
                if (!monthMatcher.find()) {
                    String dayString = dayMonth.replaceFirst(month, "");
                    final Matcher dayMatcher = dayPattern.matcher(dayString);
                    if (dayMatcher.find()) {
                        // we are done
                        String day = dayMatcher.group();
                        try {
                            SimpleDateFormat sdFormatLocal = new SimpleDateFormat(SDF_STRING);
                            result = sdFormatLocal.parse(year + "-" + month + "-" + day);

                        } catch (ParseException e) {
                        }
                    }
                }
            }
        }
        return result;
    }

    public static LocalDate toLocalDate() {
        Date date = new Date();
        return toLocalDate(date);
    }

    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date fromLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date atStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    public static Date atEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

}
