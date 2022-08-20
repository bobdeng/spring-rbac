package cn.bobdeng.rbac.archtype;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

public class SystemDate {

    private SystemDate() {

    }

    private static Supplier<Long> nowSupplier = System::currentTimeMillis;

    public static void setNowSupplier(Supplier<Long> nowSupplier) {
        SystemDate.nowSupplier = nowSupplier;
    }

    public static void setNowSupplier(String date) {
        setNowSupplier(() -> parse(date));
    }

    public static long now() {
        return nowSupplier.get();
    }

    public static long parse(String date) {
        return parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static long parse(String date, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
