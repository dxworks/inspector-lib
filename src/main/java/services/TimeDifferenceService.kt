package services

import java.util.*

class TimeDifferenceService {
    fun differenceBetweenDates(startDate: Date, endDate: Date): String {
        val differenceInTime = endDate.time - startDate.time
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
}