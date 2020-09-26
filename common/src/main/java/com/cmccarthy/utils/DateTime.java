package com.cmccarthy.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

import static java.time.OffsetDateTime.now;

@SuppressWarnings("unused")
public class DateTime {

    private static final DateTimeFormatter ISO_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter ISO_DATE_FORMAT_NO_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static OffsetDateTime getEffectiveDate() {
        return getOffsetDateTime(2018, 1, 1);
    }

    public static OffsetDateTime getExpirationDate() {
        return getOffsetDateTime(3000, 1, 1);
    }

    public static OffsetDateTime getOffsetDateTime(int year, int month, int dayOfMonth) {
        return OffsetDateTime.of(LocalDate.of(year, month, dayOfMonth), LocalTime.of(0, 0), ZoneOffset.UTC);
    }

    public static OffsetDateTime localDateTimeNow() {
        return OffsetDateTime.of(now().toLocalDateTime(), ZoneOffset.UTC);
    }

    public static OffsetDateTime getActivationDate(String dateAsString) {
        LocalDateTime dt = LocalDateTime.parse(dateAsString, ISO_DATE_TIME_FORMAT);
        return OffsetDateTime.of(
                LocalDate.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth()),
                LocalTime.of(dt.getHour(), dt.getMinute(), dt.getSecond()),
                ZoneOffset.UTC);
    }

    /**
     * @return - get month date as 01-2020 String
     */
    public static String getMonthYearNumerical() {
        return now().toString().split("-")[1] + "/" + now().getYear();
    }

    /**
     * @return next week day as 2020-06-16
     */
    public static String getNextDayOfWeekNumerical() {
        LocalDateTime date = LocalDateTime.now();
        do {
            date = date.plusDays(1);
        } while (date.getDayOfWeek().getValue() > 5);
        return date.format(ISO_DATE_FORMAT_NO_TIME);
    }
}