package services

import lombok.SneakyThrows
import java.sql.Timestamp
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

class TimeConverterService {
    fun convertTimestampToDate(timestamp: Long): Date {
        val ts = Timestamp(timestamp)
        return Date(ts.time)
    }

    @SneakyThrows
    fun convertISO8061ToDate(time: String?): Date {
        val ta = DateTimeFormatter.ISO_INSTANT.parse(time)
        val i = Instant.from(ta)
        return Date.from(i)
    }
}