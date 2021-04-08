package services;

import lombok.SneakyThrows;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class TimeConverterService {

    public Date convertTimestampToDate(long timestamp) {
        Timestamp ts = new Timestamp(timestamp);
        return new Date(ts.getTime());
    }

    @SneakyThrows
    public Date convertISO_8061ToDate(String time) {
        TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(time);
        Instant i = Instant.from(ta);
        return Date.from(i);
    }
}
