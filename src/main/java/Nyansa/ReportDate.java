package Nyansa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ReportDate {
    private static final long secondsInDay = TimeUnit.DAYS.toSeconds(1);
    private static final String formatStr = "MM/dd/yyyy";
    private static final DateFormat dateFormat = new SimpleDateFormat(formatStr);

    static {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public static String asGmtStr(long epochTimeSec) {
        Date date = new Date(epochTimeSec *1000);
        return dateFormat.format(date).concat(" GMT");
    }

    public static long getStartOfEpochDay(long epochTimeSec) {
        return epochTimeSec - epochTimeSec % secondsInDay;
    }
}
