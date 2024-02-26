package net.arial.time;

public class Timer {

    public String prefix = "timeManager";

    public Timer() {}

    public Timer(String prefix) {
        this.prefix = prefix;
    }

    public void setPrefix(String prefix) {
        if (prefix == null) {
            throw new NullPointerException("Prefix can not be null!");
        }
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        if (seconds <= 0) return placeSeconds(Time.config.getString(prefix + ".config.onlySeconds"), 0);
        if (seconds < 60) return placeSeconds(Time.config.getString(prefix + ".config.onlySeconds"), seconds);

        long minutes = seconds / 60;
        long newSeconds = seconds - (60 * minutes);
        if (seconds < 3600) {
            if (seconds % 60 == 0) return placeMinutes(Time.config.getString(prefix + ".config.onlyMinutes"), minutes);

            String secondsPlaced = placeSeconds(Time.config.getString(prefix + ".config.secondsMinutes"), newSeconds);
            return placeMinutes(secondsPlaced, minutes);
        }

        long hours = seconds / 3600;
        long newMinutes = minutes - (60 * hours);
        if (seconds < 86400) {
            if (newSeconds == 0 && newMinutes == 0)
                return placeHours(Time.config.getString(prefix + ".config.onlyHours"), hours);
            if (newSeconds == 0) {
                String minutesPlaced = placeMinutes(Time.config.getString(prefix + ".config.minutesHours"), newMinutes);
                return placeHours(minutesPlaced, hours);
            }
            if (newMinutes == 0) {
                String secondsPlaced = placeSeconds(Time.config.getString(prefix + ".config.secondsHours"), newSeconds);
                return placeHours(secondsPlaced, hours);
            }

            String secondsPlaced = placeSeconds(Time.config.getString(prefix + ".config.secondsMinutesHours"), newSeconds);
            String minutesPlaced = placeMinutes(secondsPlaced, newMinutes);
            return placeHours(minutesPlaced, hours);
        }

        long days = seconds / 86400;
        long newHours = hours - (24 * days);
        if (newSeconds == 0 && newMinutes == 0 && newHours == 0)
            return placeDays(Time.config.getString(prefix + ".config.onlyDays"), days);
        if (newSeconds == 0 && newMinutes == 0) {
            String hoursPlaced = placeHours(Time.config.getString(prefix + ".config.hoursDays"), newHours);
            return placeDays(hoursPlaced, days);
        }
        if (newSeconds == 0 && newHours == 0) {
            String minutesPlaced = placeMinutes(Time.config.getString(prefix + ".config.minutesDays"), newMinutes);
            return placeDays(minutesPlaced, days);
        }
        if (newMinutes == 0 && newHours == 0) {
            String secondsPlaced = placeMinutes(Time.config.getString(prefix + ".config.secondsDays"), newSeconds);
            return placeDays(secondsPlaced, days);
        }

        if (newSeconds == 0) {
            String minutesPlaced = placeMinutes(Time.config.getString(prefix + ".config.minutesHoursDays"), newMinutes);
            String hoursPlaced = placeHours(minutesPlaced, newHours);
            return placeDays(hoursPlaced, days);
        }
        if (newMinutes == 0) {
            String secondsPlaced = placeSeconds(Time.config.getString(prefix + ".config.secondsHoursDays"), newSeconds);
            String hoursPlaced = placeHours(secondsPlaced, newHours);
            return placeDays(hoursPlaced, days);
        }
        if (newHours == 0) {
            String secondsPlaced = placeSeconds(Time.config.getString(prefix + ".config.secondsMinutesDays"), newSeconds);
            String minutesPlaced = placeMinutes(secondsPlaced, newMinutes);
            return placeDays(minutesPlaced, days);
        }

        String secondsPlaced = placeSeconds(Time.config.getString(prefix + ".config.secondsMinutesHoursDays"), newSeconds);
        String minutesPlaced = placeMinutes(secondsPlaced, newMinutes);
        String hoursPlaced = placeHours(minutesPlaced, newHours);
        return placeDays(hoursPlaced, days);
    }

    private String placeSeconds(String message, long seconds) {
        String time = message.replace("%seconds%", String.valueOf(seconds));
        if (seconds == 1) return time.replace("%seconds_placeholder%", Time.config.getString(prefix + ".second"));
        else return time.replace("%seconds_placeholder%", Time.config.getString(prefix + ".seconds"));
    }

    private String placeMinutes(String message, long minutes) {
        String time = message.replace("%minutes%", String.valueOf(minutes));
        if (minutes == 1) return time.replace("%minutes_placeholder%", Time.config.getString(prefix + ".minute"));
        else return time.replace("%minutes_placeholder%", Time.config.getString(prefix + ".minutes"));
    }

    private String placeHours(String message, long hours) {
        String time = message.replace("%hours%", String.valueOf(hours));
        if (hours == 1) return time.replace("%hours_placeholder%", Time.config.getString(prefix + ".hour"));
        else return time.replace("%hours_placeholder%", Time.config.getString(prefix + ".hours"));
    }

    private String placeDays(String message, long days) {
        String time = message.replace("%days%", String.valueOf(days));
        if (days == 1) return time.replace("%days_placeholder%", Time.config.getString(prefix + ".day"));
        else return time.replace("%days_placeholder%", Time.config.getString(prefix + ".days"));
    }
}
