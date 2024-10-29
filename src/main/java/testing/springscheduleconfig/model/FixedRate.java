package testing.springscheduleconfig.model;

public class FixedRate {
    private int days;
    private int hours;
    private int seconds;

    public FixedRate() {}
    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return "Periodic{" +
                "days=" + days +
                ", hours=" + hours +
                ", seconds=" + seconds +
                '}';
    }
}
