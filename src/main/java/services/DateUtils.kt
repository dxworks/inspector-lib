package services

import java.time.Instant
import java.time.Period
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


val DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy")


fun convertTimestampToDate(timestamp: Long): ZonedDateTime {
    return ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
}

fun convertISO8061ToDate(time: String): ZonedDateTime {
    val ta = DateTimeFormatter.ISO_INSTANT.parse(time)
    val i = Instant.from(ta)
    return ZonedDateTime.ofInstant(i, ZoneId.systemDefault())
}

fun differenceBetweenDates(startDate: ZonedDateTime, endDate: ZonedDateTime): String {
    val differenceInTime = endDate.toInstant().toEpochMilli() - startDate.toInstant().toEpochMilli()
    if (differenceInTime == 0L)
        return "Version is up-to-date"

    val differenceInSeconds = differenceInTime / 1000 % 60
    val differenceInMinutes = differenceInTime / (1000 * 60) % 60
    val differenceInHours = differenceInTime / (1000 * 60 * 60) % 24
    val differenceInDays = differenceInTime / (1000 * 60 * 60 * 24) % 365
    val differenceInYears = differenceInTime / (1000L * 60 * 60 * 24 * 365)
    return (differenceInYears.toString() + " years "
            + differenceInDays + " days "
            + differenceInHours + " hours "
            + differenceInMinutes + " minutes "
            + differenceInSeconds + " seconds")
}

fun differenceBetweenDatesInMonths(startDate: ZonedDateTime, endDate: ZonedDateTime): Long {
    return Period.between(startDate.toLocalDate(), endDate.toLocalDate()).toTotalMonths()
}