package cn.bobdeng.rbac.archtype;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

public class SystemDate {
    public static final long ONE_DAY = 86400 * 1000L;
    public static final long ONE_MINUTE = 60 * 1000L;


    private SystemDate() {

    }

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_WITH_HOUR = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static Supplier<Long> nowSupplier = System::currentTimeMillis;

    public static void setNowSupplier(Supplier<Long> nowSupplier) {
        SystemDate.nowSupplier = nowSupplier;
    }

    public static long now() {
        return nowSupplier.get();
    }

    public static String timeOfNow() {
        return now(SystemDate.TIME_FORMATTER);
    }

    public static String now(DateTimeFormatter formatter) {
        return Instant.ofEpochMilli(now()).atZone(ZoneId.systemDefault()).format(formatter);
    }

    public static String parse(long time) {
        return parse(time, DATE_FORMATTER);
    }

    public static String parse(long time, DateTimeFormatter dateTimeFormatter) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).format(dateTimeFormatter);
    }

    public static String today() {
        return now(DATE_FORMATTER);
    }

    public static void setNowSupplier(String date) {
        setNowSupplier(() -> parse(date));
    }

    public static long parse(String date) {
        return parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static long parse(String date, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static String oneWeekAgo() {
        return getDateTimeOfNow(now()).minusDays(7).format(DATE_FORMATTER);
    }

    public static String oneMonthAgo() {
        return getDateTimeOfNow(now()).minusMonths(1).format(DATE_FORMATTER);
    }

    public static String oneMonthLater() {
        return getDateTimeOfNow(now()).plusMonths(1).format(DATE_FORMATTER);
    }

    private static ZonedDateTime getDateTimeOfNow(long now) {
        return Instant.ofEpochMilli(now).atZone(ZoneId.systemDefault());
    }

    public static String format(long timestamp, DateTimeFormatter formatter) {
        return getDateTimeOfNow(timestamp).format(formatter);
    }

    public static long toTimestamp(String date) {
        LocalDate localDate = LocalDate.parse(date, SystemDate.DATE_FORMATTER);
        return localDate.atTime(0, 0, 0)
                .atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
    }

    public static long toTimestamp(String date, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
    }

}
