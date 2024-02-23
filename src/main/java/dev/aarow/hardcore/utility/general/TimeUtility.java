package dev.aarow.hardcore.utility.general;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtility {

    public static String convertToTimer(long durationInMillis) {
        long days = TimeUnit.MILLISECONDS.toDays(durationInMillis);
        long hours = TimeUnit.MILLISECONDS.toHours(durationInMillis) - TimeUnit.DAYS.toHours(days);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(durationInMillis) - TimeUnit.HOURS.toMinutes(hours) - TimeUnit.DAYS.toMinutes(days);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(durationInMillis) - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.DAYS.toSeconds(days);
        StringBuilder timerString = new StringBuilder();
        if (days > 0L)
            timerString.append(String.format("%02d:", new Object[] { Long.valueOf(days) }));
        if (hours > 0L || days > 0L)
            timerString.append(String.format("%02d:", new Object[] { Long.valueOf(hours) }));
        timerString.append(String.format("%02d:", new Object[] { Long.valueOf(minutes) })).append(String.format("%02d", new Object[] { Long.valueOf(seconds) }));
        return timerString.toString();
    }

    public static long convertStringToMillis(String inputStr) {
        Pattern pattern = Pattern.compile("(?:(\\d+)d)?(?:(\\d+)h)?(?:(\\d+)m)?(?:(\\d+)s)?");
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            int days = (matcher.group(1) != null) ? Integer.parseInt(matcher.group(1)) : 0;
            int hours = (matcher.group(2) != null) ? Integer.parseInt(matcher.group(2)) : 0;
            int minutes = (matcher.group(3) != null) ? Integer.parseInt(matcher.group(3)) : 0;
            int seconds = (matcher.group(4) != null) ? Integer.parseInt(matcher.group(4)) : 0;
            return ((days * 24 * 60 * 60 + hours * 60 * 60 + minutes * 60 + seconds) * 1000);
        }
        throw new IllegalArgumentException("Invalid input format");
    }
}
