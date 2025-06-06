package example.weather.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DB_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter UI_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static String toDatabaseFormat(LocalDateTime dateTime) {
        return dateTime.format(DB_FORMATTER);
    }

    public static LocalDateTime fromDatabaseFormat(String dateString) {
        return LocalDateTime.parse(dateString, DB_FORMATTER);
    }

    public static String toUserFriendlyFormat(LocalDateTime dateTime) {
        return dateTime.format(UI_FORMATTER);
    }
}