package uz.guideme.hotelbooking.service.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtils {

    public static Date getDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();

        return Date.from(instant);
    }

}
