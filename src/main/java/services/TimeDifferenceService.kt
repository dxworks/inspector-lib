package services;

import java.util.Date;

public class TimeDifferenceService {

    public String differenceBetweenDates(Date startDate, Date endDate) {
        long differenceInTime = endDate.getTime() - startDate.getTime();
        long differenceInSeconds = (differenceInTime / 1000) % 60;
        long differenceInMinutes = (differenceInTime / (1000 * 60)) % 60;
        long differenceInHours = (differenceInTime / (1000 * 60 * 60)) % 24;
        long differenceInDays = (differenceInTime / (1000 * 60 * 60 * 24)) % 365;
        long differenceInYears = (differenceInTime / (1000L * 60 * 60 * 24 * 365));

        return differenceInYears + " years, "
                + differenceInDays + " days, "
                + differenceInHours + " hours, "
                + differenceInMinutes + " minutes, "
                + differenceInSeconds + " seconds";
    }
}
